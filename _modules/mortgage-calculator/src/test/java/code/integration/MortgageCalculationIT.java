package code.integration;

import code.TestApplicationConfiguration;
import code.service.MortgageCalculationService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static code.fixtures.InputDataValues.inputDataForPrinting;

@SpringJUnitConfig(classes = {TestApplicationConfiguration.class})
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MortgageCalculationIT {

   MortgageCalculationService mortgageCalculationService;

   private static final Path OUTPUT_PATH = Path.of("src/main/resources/OutputData.txt");
   private static final Path TEST_SAMPLE_PATH = Path.of("src/test/resources/Output.txt");

   @Test
   @DisplayName("contents of calculation output should match previously calculated output")
   void testPrinting() {
      mortgageCalculationService.calculate(inputDataForPrinting);
      try (BufferedReader outputReader = Files.newBufferedReader(OUTPUT_PATH);
           BufferedReader sampleReader = Files.newBufferedReader(TEST_SAMPLE_PATH)
      ) {
         do  {
            Assertions.assertEquals(outputReader.readLine(), sampleReader.readLine());
         } while (Objects.nonNull(sampleReader.readLine()) && Objects.nonNull(outputReader.readLine()));
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}