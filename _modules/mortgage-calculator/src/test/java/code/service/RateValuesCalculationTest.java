package code.service;

import code.model.InputData;
import code.model.OverpaymentData;
import code.model.OverpaymentType;
import code.model.rate.MortgageResidual;
import code.model.rate.RateAmounts;
import code.model.rate.TimePoint;
import code.service.rateCalculation.AmountsCalculationService;
import code.service.rateCalculation.AmountsCalculationServiceI;
import code.service.rateCalculation.ConstantAmountsCalculationService;
import code.service.rateCalculation.DecreasingAmountsCalculationService;
import code.service.rateCalculation.ResidualCalculationService;
import code.service.rateCalculation.ResidualCalculationServiceI;
import code.service.rateCalculation.TimePointService;
import code.service.rateCalculation.TimePointServiceI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static code.fixtures.InputDataValues.constantID;
import static code.fixtures.InputDataValues.decreasingID;
import static code.fixtures.RateValues.expectedConstantMRWithReduceDurationOverpayment;
import static code.fixtures.RateValues.expectedConstantRA;
import static code.fixtures.RateValues.expectedDecreasingMR;
import static code.fixtures.RateValues.expectedDecreasingMRWithReduceDurationOverpayment;
import static code.fixtures.RateValues.expectedDecreasingRA;
import static code.fixtures.RateValues.expectedMRWithReduceRateOverpayment;
import static code.fixtures.RateValues.expectedTP;
import static code.fixtures.RateValues.expectedTPWithOverpayment;
import static code.fixtures.RateValues.previousConstantMR;
import static code.fixtures.RateValues.previousDecreasingMR;

class RateValuesCalculationTest {

    public static final AmountsCalculationService amountsCalculationService = new AmountsCalculationService(
            new ConstantAmountsCalculationService(),
            new DecreasingAmountsCalculationService()
    );

    public static final ResidualCalculationService residualCalculationService = new ResidualCalculationService();

    @BeforeEach()
    void setUp() {
        InputData.Scales.ROUNDING_SCALE = 10;
        InputData.Scales.DISPLAYING_SCALE = 2;
    }

    @Test
    @DisplayName("should calculate time point")
    void testTimePointCalculate() {
        //given
        TimePointService service = new TimePointService();
        //when
        TimePoint calculatedTP = service.calculate(expectedTP.getRateNumber(), constantID);
        //then
        Assertions.assertEquals(expectedTP.getDate(), calculatedTP.getDate());
        Assertions.assertEquals(expectedTP.getMonth(), calculatedTP.getMonth());
        Assertions.assertEquals(expectedTP.getYear(), calculatedTP.getYear());
        Assertions.assertEquals(expectedTP.getHasOverpayment(), calculatedTP.getHasOverpayment());
        Assertions.assertEquals(expectedTP.getRateNumber(), calculatedTP.getRateNumber());
    }


    @Test
    @DisplayName("should calculate CONSTANT rate amounts")
    void testConstantRateAmountsCalculation() {
        //given
        //when
        RateAmounts calculatedRA = amountsCalculationService.calculate(
                constantID, previousConstantMR);
        //then
        Assertions.assertEquals(expectedConstantRA.getRateAmount(), calculatedRA.getRateAmount());
        Assertions.assertEquals(expectedConstantRA.getInterestAmount(), calculatedRA.getInterestAmount());
        Assertions.assertEquals(expectedConstantRA.getCapitalAmount(), calculatedRA.getCapitalAmount());
    }

    @Test
    @DisplayName("should calculate DECREASING rate amounts")
    void testDecreasingRateAmountsCalculation() {
        //given
        //when
        RateAmounts calculatedRA = amountsCalculationService.calculate(
                decreasingID, previousDecreasingMR);
        //then
        Assertions.assertEquals(expectedDecreasingRA.getRateAmount(), calculatedRA.getRateAmount());
        Assertions.assertEquals(expectedDecreasingRA.getInterestAmount(), calculatedRA.getInterestAmount());
        Assertions.assertEquals(expectedDecreasingRA.getCapitalAmount(), calculatedRA.getCapitalAmount());
    }

    @Test
    @DisplayName("should calculate mortgage residual WITHOUT overpayment (same for constant and decreasing)")
    void testDecreasingMortgageResidualCalculation() {
        //given
        //when
        MortgageResidual calculatedMR = residualCalculationService.calculate(
                decreasingID, expectedTP, expectedDecreasingRA, previousDecreasingMR);
        //then
        Assertions.assertEquals(expectedDecreasingMR.getResidualAmount(),calculatedMR.getResidualAmount());
        Assertions.assertEquals(expectedDecreasingMR.getResidualDuration(), calculatedMR.getResidualDuration());
    }

    @Test
    @DisplayName("should calculate mortgage residual with reduce RATE overpayment (same for constant and decreasing)")
    void testMRWithReduceRateOverpayment() {
        //given
        InputData iDWithOverpayment = decreasingID.withOverpaymentMap(Map.of(
                BigDecimal.valueOf(20), OverpaymentData.of(BigDecimal.valueOf(1000), OverpaymentType.REDUCE_RATE)
        ));
        //when
        MortgageResidual calculatedMR = residualCalculationService.calculate(
                iDWithOverpayment, expectedTPWithOverpayment, expectedDecreasingRA, previousDecreasingMR);
        //then
        Assertions.assertEquals(expectedMRWithReduceRateOverpayment.getResidualAmount(),calculatedMR.getResidualAmount());
        Assertions.assertEquals(expectedMRWithReduceRateOverpayment.getResidualDuration(), calculatedMR.getResidualDuration());
    }


    @Test
    @DisplayName("should calculate CONSTANT mortgage residual with reduce DURATION overpayment")
    void testMRWithConstantReduceDurationOverpayment() {
        //given
        InputData constantIDWithOverpayment = constantID.withOverpaymentMap(Map.of(
                BigDecimal.valueOf(20), OverpaymentData.of(BigDecimal.valueOf(1000), OverpaymentType.REDUCE_DURATION)
        ));
        //when
        MortgageResidual calculatedMR = residualCalculationService.calculate(
                constantIDWithOverpayment, expectedTPWithOverpayment, expectedConstantRA, previousConstantMR);
        //then
        Assertions.assertEquals(expectedConstantMRWithReduceDurationOverpayment.getResidualAmount(),calculatedMR.getResidualAmount());
        Assertions.assertEquals(expectedConstantMRWithReduceDurationOverpayment.getResidualDuration(), calculatedMR.getResidualDuration());
    }

    @Test
    @DisplayName("should calculate DECREASING mortgage residual with reduce DURATION overpayment")
    void testMRWithDecreasingReduceDurationOverpayment() {
        //given
        InputData decreasingIDWithOverpayment = decreasingID.withOverpaymentMap(Map.of(
                BigDecimal.valueOf(20), OverpaymentData.of(BigDecimal.valueOf(1000), OverpaymentType.REDUCE_DURATION)
        ));
        //when
        MortgageResidual calculatedMR = residualCalculationService.calculate(
                decreasingIDWithOverpayment, expectedTPWithOverpayment, expectedDecreasingRA, previousDecreasingMR);
        //then
        Assertions.assertEquals(expectedDecreasingMRWithReduceDurationOverpayment.getResidualAmount(),calculatedMR.getResidualAmount());
        Assertions.assertEquals(expectedDecreasingMRWithReduceDurationOverpayment.getResidualDuration(), calculatedMR.getResidualDuration());
    }
}