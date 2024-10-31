package code.service.input;

import code.model.InputData;
import code.model.OverpaymentData;
import code.model.OverpaymentType;
import code.model.RateType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

@Service
public class InputFromBuilder implements InputServiceI {
   @Override
   public InputData load() {
      return InputData.defaultInputData()
              .withRepaymentStartDate(LocalDate.now())
              .withStartingAmount(BigDecimal.valueOf(150000))
              .withStartingDuration(BigDecimal.valueOf(60))
              .withInterestPercent(BigDecimal.valueOf(15))
              .withRateType(RateType.CONSTANT)
              .withOverpaymentProvisionPercent(BigDecimal.valueOf(10.1))
              .withOverpaymentProvisionMonths(BigDecimal.valueOf(120))
              .withOverpaymentMap(new TreeMap<>(Map.of(
                      BigDecimal.valueOf(2), OverpaymentData.of(BigDecimal.valueOf(10000), OverpaymentType.REDUCE_DURATION),
                      BigDecimal.valueOf(6), OverpaymentData.of(BigDecimal.valueOf(10000), OverpaymentType.REDUCE_RATE),
                      BigDecimal.valueOf(7), OverpaymentData.of(BigDecimal.valueOf(10000), OverpaymentType.REDUCE_DURATION),
                      BigDecimal.valueOf(10), OverpaymentData.of(BigDecimal.valueOf(10000), OverpaymentType.REDUCE_RATE),
                      BigDecimal.valueOf(11), OverpaymentData.of(BigDecimal.valueOf(10000), OverpaymentType.REDUCE_DURATION),
                      BigDecimal.valueOf(14), OverpaymentData.of(BigDecimal.valueOf(10000), OverpaymentType.REDUCE_DURATION)
              )))
              .withDoPrint(true)
              .withDoAddYearSeparator(true)
              .withDoCalculateSummary(true)
              .withCalculationCycle(BigDecimal.valueOf(1))
              ;
   }
}