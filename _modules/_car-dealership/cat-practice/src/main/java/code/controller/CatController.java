package code.controller;

import code.infrastructure.database.entity.CatEntity;
import code.infrastructure.database.repository.jpa.CatJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Controller
@RequestMapping("/cats")
@AllArgsConstructor
public class CatController {

   private final CatJpaRepository catJpaRepository;

   @GetMapping
   public String employees(Model model) {
      List<CatEntity> list = catJpaRepository.findAll();
      model.addAttribute("catList", list);
      return "cats";
   }

   @GetMapping("/show/{catId}")
   public String showCatDetails(
           @PathVariable(name = "catId") Integer catId,
           Model model
   ) {
      CatEntity catEntity = catJpaRepository.findById(catId)
              .orElseThrow(EntityNotFoundException::new);
      model.addAttribute("foundCat", catEntity);
      return "catDetails";
   }

   @PostMapping("/add")
   public String addCat(
           @RequestParam(value = "name") String name,
           @RequestParam(value = "salary") String salary
   ) {
      CatEntity newCat = CatEntity.builder()
              .name(name)
              .salary(new BigDecimal(salary))
              .happyDay(ZonedDateTime.now())
              .build();
      catJpaRepository.save(newCat);
      return "redirect:/cats";
   }

   @PostMapping("/delete/{catId}")
   public String deleteCat(
           @PathVariable(name = "catId") Integer catId,
           Model model
   ) {
      catJpaRepository.delete(catJpaRepository.findById(catId)
              .orElseThrow(EntityNotFoundException::new));
      return "redirect:/cats";
   }
}