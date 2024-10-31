package code.service;

import code.model.InputData;
import code.model.Summary;
import code.model.rate.Rate;
import code.service.printing.PrintingServiceI;
import code.service.rateCalculation.RateCalculationServiceI;
import code.service.serialization.SerializationServiceI;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Value
@Slf4j
@Service
public class MortgageCalculationService implements MortgageCalculationServiceI {

   RateCalculationServiceI rateCalculationServiceI;
   SummaryCalculationServiceI summaryCalculationServiceI;
   PrintingServiceI printingServiceI;
   SerializationServiceI serializationServiceI;

   @Override
   public void calculate(InputData inputData) {
      log.info("MortgageCalculation Start");
      List<Rate> rates = rateCalculationServiceI.calculate(inputData);
      Summary summary = inputData.isDoCalculateSummary() ? summaryCalculationServiceI.calculate(rates, inputData) : null;
      if(inputData.isDoPrint()) printingServiceI.printData(inputData, rates, summary);
      if(inputData.isDoSerialize()) serializationServiceI.serialize(rates);
      log.info("MortgageCalculation End");
   }
}