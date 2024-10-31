package code.service.rateCalculation;

import code.model.InputData;
import code.model.rate.RateAmounts;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static code.model.InputData.Scales.ROUNDING_SCALE;
import static code.service.rateCalculation.AmountsCalculationServiceI.calculateInterestAmount;
import static code.service.rateCalculation.AmountsCalculationServiceI.calculateMortgageConstant;

@Service
public class ConstantAmountsCalculationService implements ConstantAmountsCalculationServiceI {

    @Override
    public RateAmounts calculate(InputData inputData, BigDecimal residualAmount) {
        BigDecimal interestPercent = inputData.getReference().getReferenceInterestPercent();
        BigDecimal c = calculateMortgageConstant(interestPercent);

        BigDecimal startingAmount = inputData.getReference().getReferenceAmount();
        BigDecimal residualDuration = inputData.getReference().getReferenceDuration();

        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal rateAmount = calculateRateAmount(
                c, startingAmount, interestAmount, residualAmount, residualDuration);
        BigDecimal capitalAmount = calculateCapitalAmount(rateAmount, interestAmount, residualAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount);
    }

    private BigDecimal calculateRateAmount(
            BigDecimal c,
            BigDecimal startingAmount,
            BigDecimal interestAmount,
            BigDecimal residualAmount,
            BigDecimal residualDuration
            ) {
        BigDecimal rateAmount = startingAmount
                .multiply(c.pow(residualDuration.intValue()))
                .multiply(c.subtract(BigDecimal.ONE))
                .divide(c.pow(residualDuration.intValue()).subtract(BigDecimal.ONE), ROUNDING_SCALE, RoundingMode.HALF_UP);
        return compareWithResidual(rateAmount, interestAmount, residualAmount);
    }

    private BigDecimal compareWithResidual(
            BigDecimal rateAmount,
            BigDecimal interestAmount,
            BigDecimal residualAmount
    ) {
        if (rateAmount.subtract(interestAmount).compareTo(residualAmount) >= 0) {
            return residualAmount.add(interestAmount);
        }
        return rateAmount;
    }

    private BigDecimal calculateCapitalAmount(
            BigDecimal rateAmount,
            BigDecimal interestAmount,
            BigDecimal residualAmount
    ) {
        BigDecimal capitalAmount = rateAmount.subtract(interestAmount);
        if (capitalAmount.compareTo(residualAmount) >= 0) {
            return(capitalAmount);
        }
        return capitalAmount;
    }
}
