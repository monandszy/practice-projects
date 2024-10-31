package code.fixtures;

import code.model.InputData;
import code.model.RateType;
import code.model.Reference;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

import static code.model.InputData.PERCENT;
import static code.model.InputData.Scales.ROUNDING_SCALE;

public class InputDataValues {
   // InputData
   public static InputData getTestInputData() {
      BigDecimal defaultStartingAmount = BigDecimal.valueOf(10000);
      BigDecimal defaultInterestPercent = BigDecimal.valueOf(3.6);
      BigDecimal defaultStartingDuration = BigDecimal.valueOf(100);
      return InputData.builder()
              .repaymentStartDate(LocalDate.of(0, 1, 1))
              .startingAmount(defaultStartingAmount)
              .startingDuration(defaultStartingDuration)
              .interestPercent(defaultInterestPercent)
              .reference(new Reference(
                      defaultStartingAmount,
                      defaultStartingDuration,
                      defaultInterestPercent.divide(PERCENT, ROUNDING_SCALE, RoundingMode.HALF_UP)
              ))
              .rateType(RateType.CONSTANT)
              .overpaymentProvisionPercent(BigDecimal.valueOf(0))
              .overpaymentProvisionMonths(BigDecimal.valueOf(0))
              .overpaymentMap(new TreeMap<>(Map.of()))
              .doPrint(false)
              .calculationCycle(BigDecimal.valueOf(12))
              .doAddYearSeparator(true)
              .doCalculateSummary(true)
              .build();
   }

   public static final InputData constantID = getTestInputData();
   public static final InputData decreasingID = getTestInputData().withRateType(RateType.DECREASING);
   public static final InputData inputDataForPrinting = getTestInputData().withDoPrint(true).withCalculationCycle(BigDecimal.valueOf(1));
   public static final InputData inputDataForSerialization = getTestInputData().withCalculationCycle(BigDecimal.valueOf(12));

}