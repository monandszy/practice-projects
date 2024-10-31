package code.service.rateCalculation;

import code.model.rate.MortgageResidual;
import code.model.rate.RateAmounts;
import code.model.InputData;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static code.model.InputData.Scales.ROUNDING_SCALE;


public interface AmountsCalculationServiceI {

    BigDecimal YEAR = BigDecimal.valueOf(12);

    RateAmounts calculate(InputData inputData, MortgageResidual mortgageResidual);

    static BigDecimal calculateInterestAmount(BigDecimal residualAmount, BigDecimal interestPercent) {
        return residualAmount.multiply(interestPercent).divide(YEAR, ROUNDING_SCALE, RoundingMode.HALF_UP);
    }

    static BigDecimal calculateMortgageConstant(BigDecimal interestPercent) {
        return interestPercent.divide(YEAR, ROUNDING_SCALE, RoundingMode.HALF_UP).add(BigDecimal.ONE);
    }
}