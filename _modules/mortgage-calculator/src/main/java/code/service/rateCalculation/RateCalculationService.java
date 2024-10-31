package code.service.rateCalculation;

import code.model.InputData;
import code.model.rate.MortgageResidual;
import code.model.rate.Rate;
import code.model.rate.RateAmounts;
import code.model.rate.TimePoint;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Value
@Slf4j
@Service
public class RateCalculationService implements RateCalculationServiceI {

    TimePointServiceI timePointServiceI;
    AmountsCalculationServiceI amountsCalculationServiceI;
    ResidualCalculationServiceI residualCalculationServiceI;

    @Override
    public List<Rate> calculate(InputData inputData) {
        log.info("RateCalculation Start");
        List<Rate> rates = new ArrayList<>();
        Rate previousRate = getZeroRate(inputData);
        for (
                BigDecimal index = BigDecimal.ONE;
                index.compareTo(inputData.getReference().getFinalDuration().add(BigDecimal.ONE)) < 0;
                index = index.add(BigDecimal.ONE)
        ) {
            Rate nextRate = calculateRate(index, inputData, previousRate);
            rates.add(nextRate);
            log.debug("Calculated rate: [{}]", nextRate);
            previousRate = nextRate;
        }
        List<Rate> reducedRates = rates.stream().filter(e -> e.getTimePoint().getRateNumber()
                .remainder(inputData.getCalculationCycle()).equals(BigDecimal.ZERO)).toList();
        log.info("RateCalculation End");
        return reducedRates;
    }

    private Rate getZeroRate(InputData inputData) {
        return new Rate(
                new TimePoint(inputData.getRepaymentStartDate(), BigDecimal.ZERO, BigDecimal.ZERO, false, BigDecimal.ZERO),
                new RateAmounts(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO),
                new MortgageResidual(inputData.getStartingAmount(), inputData.getStartingDuration())
        );
    }

    private Rate calculateRate(BigDecimal rateNumber, InputData inputData, Rate previousRate) {
        log.trace("CalculatingRate: {} Started", rateNumber);
        MortgageResidual previousResidual = previousRate.getMortgageResidual();
        TimePoint timePoint = timePointServiceI.calculate(rateNumber, inputData);
        log.trace("Calculated TimePoint: {}", timePoint);
        RateAmounts rateAmounts = amountsCalculationServiceI.calculate(inputData, previousResidual);
        MortgageResidual mortgageResidual = residualCalculationServiceI.calculate(inputData, timePoint, rateAmounts, previousResidual);
        log.trace("Calculated MortgageResidual: {}", mortgageResidual);
        log.trace("CalculatingRate {}: Ended", rateNumber);
        return new Rate(timePoint, rateAmounts, mortgageResidual);
    }
}