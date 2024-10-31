package code.service.rateCalculation;

import code.model.InputData;
import code.model.rate.RateAmounts;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static code.model.InputData.Scales.ROUNDING_SCALE;
import static code.service.rateCalculation.AmountsCalculationServiceI.calculateInterestAmount;

@Service
public class DecreasingAmountsCalculationService implements DecreasingAmountsCalculationServiceI {

    @Override
    public RateAmounts calculate(InputData inputData, BigDecimal residualAmount) {
        BigDecimal interestPercent = inputData.getReference().getReferenceInterestPercent();

        BigDecimal startingAmount = inputData.getReference().getReferenceAmount();
        BigDecimal startingDuration = inputData.getReference().getReferenceDuration();

        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal capitalAmount = calculateCapitalAmount(startingAmount, startingDuration, residualAmount);
        BigDecimal rateAmount = calculateRateAmount(capitalAmount, interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount);
    }

    private BigDecimal calculateRateAmount(BigDecimal capitalAmount, BigDecimal interestAmount) {
        return capitalAmount.add(interestAmount);
    }

    private BigDecimal calculateCapitalAmount(
            BigDecimal startingAmount,
            BigDecimal monthsDuration,
            BigDecimal residualAmount
    ) {
        BigDecimal capitalAmount = startingAmount.divide(monthsDuration, ROUNDING_SCALE, RoundingMode.HALF_UP);

        if (capitalAmount.compareTo(residualAmount) >= 0) {
            return(residualAmount);
        }
        return capitalAmount;
    }
}
