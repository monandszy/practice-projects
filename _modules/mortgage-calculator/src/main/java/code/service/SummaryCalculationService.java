package code.service;

import code.model.Summary;
import code.model.rate.Rate;
import code.model.InputData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Service
public class SummaryCalculationService implements SummaryCalculationServiceI {

    @Override
    public Summary calculate(final List<Rate> rates, final InputData inputData) {
        log.info("SummaryCalculation Start");
        BigDecimal interestSum = calculateSum(rates, rate -> rate.getRateAmounts().getInterestAmount());
        BigDecimal provisionSum = calculateOverpaymentProvisionSum(inputData);
        BigDecimal totalLoss = calculateTotalLoss(interestSum, provisionSum);
        log.info("SummaryCalculation End");
        return new Summary(interestSum, provisionSum, totalLoss);
    }
// TODO total capital + refactor
    private static BigDecimal calculateTotalLoss(BigDecimal interestSum, BigDecimal provisionSum) {
        return interestSum.add(provisionSum);
    }

    private static BigDecimal calculateSum(List<Rate> rates, Function<Rate, BigDecimal> function) {
        return rates.stream()
                .map(function)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static BigDecimal calculateOverpaymentProvisionSum(InputData inputData) {
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal key : inputData.getOverpaymentMap().keySet()) {
            if (inputData.getOverpaymentProvisionMonths().compareTo(key) >= 1) {
                sum = sum.add(inputData.getOverpaymentMap().get(key).getAmount()
                        .multiply(inputData.getOverpaymentProvisionPercent()));
            }
        }
        return sum;
    }

}
