package code.service.rateCalculation;

import code.model.InputData;
import code.model.rate.TimePoint;

import java.math.BigDecimal;

public interface TimePointServiceI {
    TimePoint calculate(BigDecimal rateNumber, InputData inputData);
}
