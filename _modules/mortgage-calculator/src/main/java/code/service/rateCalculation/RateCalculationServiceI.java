package code.service.rateCalculation;

import code.model.InputData;
import code.model.rate.Rate;

import java.util.List;

public interface RateCalculationServiceI {

    List<Rate> calculate(final InputData inputData);
}
