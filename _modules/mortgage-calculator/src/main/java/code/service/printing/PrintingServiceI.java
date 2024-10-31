package code.service.printing;

import code.model.InputData;
import code.model.Summary;
import code.model.rate.Rate;

import java.util.List;

@SuppressWarnings("unused")
public interface PrintingServiceI {

    String INTEREST_SUM = "INTEREST SUM";
    String OVERPAYMENT_PROVISION = "OVERPAYMENT PROVISION";
    String TOTAL_LOSS = "TOTAL LOSS";

    String REDUCE_RATE = "REDUCE_RATE";
    String REDUCE_PERIOD = "REDUCE PERIOD";
    String OVERPAYMENT_TYPE = "TYPE";
    String OVERPAYMENT_CUT = "MONTHS CUT";
    String OVERPAYMENT_SCHEMA = "OVERPAYMENT SCHEMA";
    String OVERPAYMENT_VALUE = "OVERPAYMENT";

    String SEPARATOR = createSeparator('-', 170);

    String RATE_NUMBER = "RN";
    String YEAR_STRING = "YEAR";
    String MONTH = "MONTH";
    String DATE = "DATE";
    String RATE = "RATE";
    String RATE_TYPE = "RATE TYPE";
    String INTEREST = "BANK INTEREST";
    String CAPITAL = "CAPITAL";
    String MONTHS_LEFT = "MONTHS LEFT";
    String AMOUNT_LEFT = "AMOUNT LEFT";
    String MORTGAGE_AMOUNT = "MORTGAGE AMOUNT";
    String MORTGAGE_PERIOD = "MORTGAGE PERIOD";
    String AMOUNT = "AMOUNT";


    String CURRENCY = "ZL";
    String MONTHS = "MONTHS";
    String PERCENT = "%";
    String NEW_LINE = "\n";


    void printIntro(InputData inputData);

    void printRates(List<Rate> rates, InputData inputData, Format rateFormat, Format overpaymentFormat);

    void printSummary(Summary summary);

    void printOverpaymentMap(InputData inputData, Format overpaymentFormat);

    void printData(InputData inputData, List<Rate> rates, Summary summary);

    private static String createSeparator(char sign, int length) {
        return String.valueOf(sign).repeat(Math.max(0, length));
    }

}