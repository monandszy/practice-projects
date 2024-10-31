package code.service.rateCalculation;

import code.model.InputData;
import code.model.rate.RateAmounts;

import java.math.BigDecimal;

public interface ConstantAmountsCalculationServiceI {
    RateAmounts calculate(InputData inputData, BigDecimal residualAmount);

}
