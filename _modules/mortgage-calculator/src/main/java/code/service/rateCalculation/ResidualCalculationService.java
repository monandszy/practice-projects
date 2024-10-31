package code.service.rateCalculation;

import code.model.InputData;
import code.model.Reference;
import code.model.rate.MortgageResidual;
import code.model.rate.RateAmounts;
import code.model.rate.TimePoint;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static code.model.InputData.Scales.ROUNDING_SCALE;

@Value
@Service
public class ResidualCalculationService implements ResidualCalculationServiceI {

    @Override
    public MortgageResidual calculate(
            InputData inputData,
            TimePoint timePoint,
            RateAmounts rateAmounts,
            MortgageResidual mortgageResidual
    ) {
        BigDecimal oldResidualAmount = mortgageResidual.getResidualAmount();
        BigDecimal oldResidualDuration = mortgageResidual.getResidualDuration();
        if (BigDecimal.ZERO.equals(oldResidualAmount)) {
            return new MortgageResidual(BigDecimal.ZERO, BigDecimal.ZERO);
        } else {
            BigDecimal newResidualAmount = calculateResidualAmount(rateAmounts, oldResidualAmount);
            if (timePoint.getHasOverpayment()) {
                return getOverpaymentMortgageResidual(inputData, timePoint, rateAmounts, oldResidualDuration, newResidualAmount);
            }
            BigDecimal newResidualDuration = reduceDurationByOne(oldResidualDuration);
            return new MortgageResidual(newResidualAmount, newResidualDuration);
        }
    }

    private MortgageResidual getOverpaymentMortgageResidual(InputData inputData, TimePoint timePoint, RateAmounts rateAmounts, BigDecimal oldDuration, BigDecimal newAmount) {
        BigDecimal rateNumber = timePoint.getRateNumber();
        BigDecimal residualOverpaymentAmount = calculateResidualOverpaymentAmount(inputData, rateNumber, newAmount);
        BigDecimal newDuration = switch (inputData.getRateType()) {
            case CONSTANT -> {
                yield switch (inputData.getOverpaymentType(rateNumber)) {
                    case REDUCE_DURATION -> {
                        BigDecimal residualDuration = reduceDurationOfConstant(inputData, rateAmounts, residualOverpaymentAmount);
                        updateOverpaymentCut(inputData, rateNumber, oldDuration.subtract(residualDuration));
                        yield residualDuration;
                    }
                    case REDUCE_RATE -> {
                        BigDecimal residualDuration = reduceDurationByOne(oldDuration);
                        updateReferenceAmount(inputData, residualOverpaymentAmount);
                        updateReferenceDuration(inputData, residualDuration);
                        yield residualDuration;
                    }
                };
            }
            case DECREASING -> {
                yield switch (inputData.getOverpaymentType(rateNumber)) {
                    case REDUCE_DURATION -> {
                        BigDecimal residualDuration = reduceDurationOfDecreasing(rateAmounts, residualOverpaymentAmount);
                        updateOverpaymentCut(inputData, rateNumber, oldDuration.subtract(residualDuration));
                        yield residualDuration;
                    }
                    case REDUCE_RATE -> {
                        BigDecimal residualDuration = reduceDurationByOne(oldDuration);
                        updateReferenceAmount(inputData, residualOverpaymentAmount);
                        updateReferenceDuration(inputData, residualDuration);
                        yield residualDuration;
                    }
                };
            }
        };
        return new MortgageResidual(residualOverpaymentAmount, newDuration);
    }

    private void updateOverpaymentCut(
            InputData inputData, BigDecimal rateNumber, BigDecimal residualCut) {
        BigDecimal overpaymentCut = residualCut.subtract(BigDecimal.ONE);
        inputData.getOverpaymentMap().get(rateNumber).setOverpaymentCut(overpaymentCut);
        updateFinalDuration(inputData, overpaymentCut);
    }

    private static void updateFinalDuration(InputData inputData, BigDecimal overpaymentCut) {
        Reference reference = inputData.getReference();
        reference.setFinalDuration(reference.getFinalDuration().subtract(overpaymentCut));
    }

    private BigDecimal calculateResidualAmount(
            RateAmounts rateAmounts, BigDecimal residualAmount) {
        return residualAmount
                .subtract(rateAmounts.getCapitalAmount())
                .max(BigDecimal.ZERO);
    }

    private BigDecimal calculateResidualOverpaymentAmount(
            InputData inputData, BigDecimal rateNumber, BigDecimal residualAmount) {
        BigDecimal amount = inputData.getOverpaymentAmount(rateNumber);
        return residualAmount.subtract(amount).max(BigDecimal.ZERO);
    }

    public BigDecimal reduceDurationByOne(BigDecimal residualDuration) {
        return residualDuration.subtract(BigDecimal.ONE).max(BigDecimal.ZERO);
    }

    private BigDecimal reduceDurationOfConstant(InputData inputData, RateAmounts rateAmounts, BigDecimal residualAmount) {
        BigDecimal q = AmountsCalculationServiceI.calculateMortgageConstant(inputData.getInterestPercent());
        BigDecimal xNumerator = rateAmounts.getRateAmount();
        BigDecimal xDenominator = rateAmounts.getRateAmount().subtract(residualAmount.multiply(q.subtract(BigDecimal.ONE)));
        BigDecimal x = xNumerator.divide(xDenominator, ROUNDING_SCALE, RoundingMode.HALF_UP);
        BigDecimal y = q;
        BigDecimal logX = BigDecimal.valueOf(Math.log(x.doubleValue()));
        BigDecimal logY = BigDecimal.valueOf(Math.log(y.doubleValue()));
        return logX.divide(logY, 0, RoundingMode.CEILING);
    }

    private BigDecimal reduceDurationOfDecreasing(RateAmounts rateAmounts, BigDecimal residualAmount) {
        return residualAmount.divide(rateAmounts.getCapitalAmount(), 0, RoundingMode.CEILING);
    }

    private void updateReferenceAmount(InputData inputData, BigDecimal residualAmount) {
        inputData.getReference().setReferenceAmount(residualAmount);
    }

    private void updateReferenceDuration(InputData inputData, BigDecimal residualDuration) {
        inputData.getReference().setReferenceDuration(residualDuration);
    }
}