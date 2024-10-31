package code.fixtures;

import code.model.InputData;
import code.model.RateType;
import code.model.rate.MortgageResidual;
import code.model.rate.RateAmounts;
import code.model.rate.TimePoint;
import code.service.rateCalculation.ResidualCalculationService;
import code.service.rateCalculation.ResidualCalculationServiceI;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class RateValues {

   // TimePoints
   public static final TimePoint expectedTP = new TimePoint(
           LocalDate.of(1, 8, 1),
           BigDecimal.valueOf(8),
           BigDecimal.valueOf(2),
           false,
           BigDecimal.valueOf(20)
   );
   public static final TimePoint expectedTPWithOverpayment = new TimePoint(
           LocalDate.of(1, 8, 1),
           BigDecimal.valueOf(8),
           BigDecimal.valueOf(2),
           true,
           BigDecimal.valueOf(20)
   );

   // RateAmounts
   public static final RateAmounts expectedConstantRA = new RateAmounts(
           BigDecimal.valueOf(115.8976847894),
           BigDecimal.valueOf(24.9693608765),
           BigDecimal.valueOf(90.9283239129)
   );
   public static final RateAmounts expectedDecreasingRA = new RateAmounts(
           BigDecimal.valueOf(124.3000000000),
           BigDecimal.valueOf(24.3000000000),
           BigDecimal.valueOf(100.0000000000)
   );

   // MortgageResiduals
   public static final MortgageResidual previousConstantMR = new MortgageResidual(
           BigDecimal.valueOf(8323.1202921570),
           BigDecimal.valueOf(81.0000000000)
   );
   public static final MortgageResidual previousDecreasingMR = new MortgageResidual(
           BigDecimal.valueOf(8100.0000000000),
           BigDecimal.valueOf(81.0000000000)
   );
   public static final MortgageResidual expectedDecreasingMR = new MortgageResidual(
           BigDecimal.valueOf(8000.0000000000),
           BigDecimal.valueOf(80.0000000000)
   );

   public static final MortgageResidual expectedMRWithReduceRateOverpayment = new MortgageResidual(
           BigDecimal.valueOf(7000.0000000000),
           BigDecimal.valueOf(80.0000000000)
   );
   public static final MortgageResidual expectedConstantMRWithReduceDurationOverpayment = new MortgageResidual(
           BigDecimal.valueOf(7232.1919682441),
           BigDecimal.valueOf(70.0000000000)
   );

   public static final MortgageResidual expectedDecreasingMRWithReduceDurationOverpayment = new MortgageResidual(
           BigDecimal.valueOf(7000.0000000000),
           BigDecimal.valueOf(70.0000000000)
   );

}