package code.service.rateCalculation;

import code.model.InputData;
import code.model.rate.MortgageResidual;
import code.model.rate.RateAmounts;
import lombok.Value;
import org.springframework.stereotype.Service;

@Value
@Service
public class AmountsCalculationService {

    ConstantAmountsCalculationServiceI constantAmountsCalculationServiceI;
    DecreasingAmountsCalculationServiceI decreasingAmountsCalculationServiceI;

    public RateAmounts calculate(InputData inputData, MortgageResidual mortgageResidual) {
        return switch (inputData.getRateType()) {
            case CONSTANT ->
                    constantAmountsCalculationServiceI.calculate(inputData, mortgageResidual.getResidualAmount());
            case DECREASING ->
                    decreasingAmountsCalculationServiceI.calculate(inputData, mortgageResidual.getResidualAmount());
        };
    }
}