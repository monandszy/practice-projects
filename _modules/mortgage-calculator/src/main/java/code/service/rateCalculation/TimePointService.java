package code.service.rateCalculation;

import code.model.InputData;
import code.model.rate.TimePoint;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static code.service.rateCalculation.AmountsCalculationServiceI.YEAR;

@Service
public class TimePointService implements TimePointServiceI {

    @Override
    public TimePoint calculate(BigDecimal rateNumber, InputData inputData) {
        LocalDate date = calculateDate(rateNumber, inputData);
        BigDecimal year = calculateYear(rateNumber);
        BigDecimal month = calculateMonth(rateNumber);
        Boolean hasOverpayment = calculateOverpayment(rateNumber, inputData);
        return new TimePoint(date, month, year, hasOverpayment, rateNumber);
    }

    private Boolean calculateOverpayment(BigDecimal rateNumber, InputData inputData) {
        for (BigDecimal key : inputData.getOverpaymentMap().keySet()) {
            if (rateNumber.compareTo(key) == 0) {
                return true;
            }
        }
        return false;
    }

    private LocalDate calculateDate(BigDecimal rateNumber, InputData inputData) {
        return inputData.getRepaymentStartDate().plusMonths(rateNumber.subtract(BigDecimal.ONE).intValue());
    }

    private BigDecimal calculateYear(final BigDecimal rateNumber) {
        return rateNumber.divide(YEAR, RoundingMode.UP).max(BigDecimal.ONE);
    }
    private BigDecimal calculateMonth(final BigDecimal rateNumber) {
        return BigDecimal.ZERO.equals(rateNumber.remainder(YEAR)) ? YEAR : rateNumber.remainder(YEAR);

    }
}
