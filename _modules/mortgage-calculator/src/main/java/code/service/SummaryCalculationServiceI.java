package code.service;

import code.model.InputData;
import code.model.Summary;
import code.model.rate.Rate;

import java.util.List;

public interface SummaryCalculationServiceI {
    Summary calculate(final List<Rate> rates, final InputData inputData);
}