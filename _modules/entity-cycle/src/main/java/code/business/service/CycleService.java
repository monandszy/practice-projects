package code.business.service;

import code.business.dao.AgeDAO;
import code.business.dao.CreatureDAO;
import code.business.dao.SaturationDAO;
import code.business.domain.Creature;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CycleService {

   @Getter
   @Setter
   private static Integer currentCycle = 0;
   private final static Integer priorityLimit = 10;

   private final CreatureDAO creatureDAO;
   private final SaturationDAO saturationDAO;
   private final AgeDAO ageDAO;
   private final DataCreationService dataCreationService;

   public void createCreatures() {
      Integer toBeCreatedCounter = creatureDAO.getOffspringNumber(); // remove 3 food if above threshold
      List<Creature> creatures = dataCreationService.getRandomCreatureList(toBeCreatedCounter);
      creatureDAO.addAll(creatures);
   }

   public void createCreatures(int amount) {
      List<Creature> creatures = dataCreationService.getRandomCreatureList(amount);
      creatureDAO.addAll(creatures);
   }

   public void assignFood() {
      List<Creature> prioritized = creatureDAO.getPrioritized(priorityLimit); // calculate prioritization
      dataCreationService.addFood(prioritized); // add piece of food to prioritized
      creatureDAO.updateFood(prioritized);
   }

   public void calculateSaturation() {
      List<Creature> creatures = saturationDAO.eatIfHungry();// remove food, recalculate saturation
      dataCreationService.addRandomPoisoningDebuffs(creatures); // random // abstraction layer
      saturationDAO.updateDebuffs(creatures);
      saturationDAO.killStarving(); // if saturation <= 0 and starving -> kill
      saturationDAO.addStarvationDebuff(dataCreationService.getRandomStarvationDebuff()); // one chance to survive starving kill
   }

   public void advanceAge() {
      ageDAO.advanceSaturation();
      ageDAO.advanceAge(); // move age by one
      List<Creature> aged = ageDAO.getAged();// random
      dataCreationService.addRandomAgeDebuffs(aged);
      saturationDAO.updateDebuffs(aged);
   }

   public void runCycles(int cycleNumber) {
      createCreatures(1);
      for (int i = 0; i < cycleNumber; i++) {
         createCreatures();
         assignFood();
         calculateSaturation();
         advanceAge();
         currentCycle++;
         System.out.println(creatureDAO.getAll());
         System.out.println(creatureDAO.getAll().size());
      }
   }
}