package code.service.rateCalculation;

import code.model.InputData;
import code.model.rate.MortgageResidual;
import code.model.rate.RateAmounts;
import code.model.rate.TimePoint;

public interface ResidualCalculationServiceI {
    MortgageResidual calculate(
            InputData inputData,
            TimePoint timePoint,
            RateAmounts rateAmounts,
            MortgageResidual mortgageResidual
    );

}
