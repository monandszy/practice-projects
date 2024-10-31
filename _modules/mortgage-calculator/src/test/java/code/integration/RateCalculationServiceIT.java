package code.integration;

import code.ApplicationConfiguration;
import code.TestApplicationConfiguration;
import code.fixtures.InputDataValues;
import code.model.InputData;
import code.model.rate.Rate;
import code.service.rateCalculation.RateCalculationService;
import code.service.serialization.SerializationService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;

import static code.fixtures.InputDataValues.inputDataForSerialization;

@SpringJUnitConfig(classes = {TestApplicationConfiguration.class})
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RateCalculationServiceIT {

   private RateCalculationService rateCalculationService;
   private SerializationService serializationService;

   @BeforeEach
   public void setUp() {
      Assertions.assertNotNull(rateCalculationService);
   }

   @Test
   @DisplayName("Test if newly calculated rates match previously calculated ones")
   void test() {
      //given
      Path SERIALIZED_PATH = Path.of("src/test/resources/serialized");
      List<Rate> deserializedRates = serializationService.deserialize(SERIALIZED_PATH);
      //when
      List<Rate> calculatedRates = rateCalculationService.calculate(inputDataForSerialization);
      //then
      for (int i = 0; i <deserializedRates.size(); i++) {
         Assertions.assertEquals(deserializedRates.get(i), calculatedRates.get(i));
      }
   }

   @Test
   @Disabled
   @DisplayName("in case a new sample is needed")
   void getSerializedTestSample() {
      List<Rate> calculatedRates = rateCalculationService.calculate(inputDataForSerialization.withDoSerialize(true));
      serializationService.serialize(calculatedRates);
   }
}