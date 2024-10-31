package code;

import code.model.InputData;
import code.service.MortgageCalculationService;
import code.service.MortgageCalculationServiceI;
import code.service.SummaryCalculationService;
import code.service.input.InputServiceFactory;
import code.service.printing.output.OutputServiceFactory;
import code.service.printing.ColumnFormattingService;
import code.service.printing.PrintingService;
import code.service.printing.RowFormattingService;
import code.service.printing.SizeFormattingService;
import code.service.printing.TableFormattingService;
import code.service.rateCalculation.AmountsCalculationService;
import code.service.rateCalculation.ConstantAmountsCalculationService;
import code.service.rateCalculation.DecreasingAmountsCalculationService;
import code.service.rateCalculation.RateCalculationService;
import code.service.rateCalculation.ResidualCalculationService;
import code.service.rateCalculation.TimePointService;
import code.service.serialization.SerializationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Objects;

import static code.service.input.InputServiceFactory.InputForm;
import static code.service.printing.output.OutputServiceFactory.OutputTo;

public class MortgageCalculator {

    public static void main(String[] args) {
        InputData inputData = InputServiceFactory.get(InputForm.BUILDER).load();
//        MortgageCalculator.getInstance().calculate(inputData);
        MortgageCalculator.getBean().calculate(inputData);
    }

    private static MortgageCalculationServiceI instance;

    private MortgageCalculator() {
    }

    private static MortgageCalculationServiceI getBean() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        return applicationContext.getBean(MortgageCalculationService.class);
    }

    private static MortgageCalculationServiceI getInstance() {
        if (Objects.isNull(instance)) {
            instance = new MortgageCalculationService(
                    new RateCalculationService(
                            new TimePointService(),
                            new AmountsCalculationService(
                                    new ConstantAmountsCalculationService(),
                                    new DecreasingAmountsCalculationService()
                            ),
                            new ResidualCalculationService()
                    ),
                    new SummaryCalculationService(),
                    new PrintingService(
                            new TableFormattingService(
                                    SizeFormattingService.builder().build(),
                                    ColumnFormattingService.builder().build(),
                                    new RowFormattingService()
                            ),
                            OutputServiceFactory.get(OutputTo.CONSOLE)
                    ),
                    new SerializationService()
            );
        }
        return instance;
    }

}