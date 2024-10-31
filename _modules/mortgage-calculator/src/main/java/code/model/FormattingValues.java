package code.model;

import code.model.rate.Rate;
import code.service.printing.FormattingFunction;

import java.math.RoundingMode;
import java.util.List;

import static code.model.InputData.Scales.DISPLAYING_SCALE;
import static code.service.printing.PrintingServiceI.AMOUNT_LEFT;
import static code.service.printing.PrintingServiceI.CAPITAL;
import static code.service.printing.PrintingServiceI.CURRENCY;
import static code.service.printing.PrintingServiceI.DATE;
import static code.service.printing.PrintingServiceI.INTEREST;
import static code.service.printing.PrintingServiceI.INTEREST_SUM;
import static code.service.printing.PrintingServiceI.MONTH;
import static code.service.printing.PrintingServiceI.MONTHS;
import static code.service.printing.PrintingServiceI.MONTHS_LEFT;
import static code.service.printing.PrintingServiceI.MORTGAGE_AMOUNT;
import static code.service.printing.PrintingServiceI.MORTGAGE_PERIOD;
import static code.service.printing.PrintingServiceI.OVERPAYMENT_CUT;
import static code.service.printing.PrintingServiceI.OVERPAYMENT_PROVISION;
import static code.service.printing.PrintingServiceI.OVERPAYMENT_TYPE;
import static code.service.printing.PrintingServiceI.OVERPAYMENT_VALUE;
import static code.service.printing.PrintingServiceI.PERCENT;
import static code.service.printing.PrintingServiceI.RATE;
import static code.service.printing.PrintingServiceI.RATE_NUMBER;
import static code.service.printing.PrintingServiceI.RATE_TYPE;
import static code.service.printing.PrintingServiceI.TOTAL_LOSS;
import static code.service.printing.PrintingServiceI.YEAR_STRING;

@SuppressWarnings("Convert2MethodRef")
public class FormattingValues {

   public static final FormattingFunction<String> DEFAULT_DESCRIBER = e -> e;

   public static final List<FormattingFunction<OverpaymentData>> overpaymentDescribers = List.of(
           overpaymentData -> overpaymentData.getAmount(),
           overpaymentData -> overpaymentData.getOverpaymentType(),
           overpaymentData -> overpaymentData.getOverpaymentCut().setScale(DISPLAYING_SCALE, RoundingMode.HALF_UP)
   );

   public static final List<FormattingFunction<OverpaymentData>> emptyOverpaymentDescribers = List.of(
           overpaymentData -> " ",
           overpaymentData -> " ",
           overpaymentData -> " "
   );

   public static final List<String> overpaymentKeys = List.of(
           OVERPAYMENT_VALUE,
           OVERPAYMENT_TYPE,
           OVERPAYMENT_CUT
   );

   public static final List<String> emptyOverpaymentKeys = List.of(
           "".repeat(OVERPAYMENT_VALUE.length()),
           "".repeat(OVERPAYMENT_TYPE.length()),
           "".repeat(OVERPAYMENT_CUT.length())
   );

   public static final List<FormattingFunction<Rate>> rateDescribers = List.of(
           (rate -> (rate.getTimePoint().getRateNumber())),
           (rate -> (rate.getTimePoint().getDate())),
           (rate -> (rate.getTimePoint().getYear())),
           (rate -> rate.getTimePoint().getMonth()),
           (rate -> rate.getRateAmounts().getRateAmountDisplay()),
           (rate -> rate.getRateAmounts().getInterestAmountDisplay()),
           (rate -> rate.getRateAmounts().getCapitalAmountDisplay()),
           (rate -> ""),
           (rate -> rate.getMortgageResidual().getResidualAmountDisplay()),
           (rate -> rate.getMortgageResidual().getResidualDurationDisplay())
   );

   public static final List<String> rateKeys = List.of(
           RATE_NUMBER,
           DATE,
           YEAR_STRING,
           MONTH,
           RATE,
           INTEREST,
           CAPITAL,
           "",
           AMOUNT_LEFT,
           MONTHS_LEFT
   );


   public static final List<String> summaryLabels = List.of(
           INTEREST_SUM,
           OVERPAYMENT_PROVISION,
           TOTAL_LOSS
   );

   public static final List<FormattingFunction<Summary>> summaryDescribers = List.of(
           summary -> summary.getInterestSumDisplay(),
           summary -> summary.getOverpaymentProvisionSumDisplay(),
           summary -> summary.getTotalLossDisplay()
   );

   public static final List<String> summaryAppendixes = List.of(
           CURRENCY,
           CURRENCY,
           CURRENCY
   );

   public static final List<String> introLabels = List.of(
           MORTGAGE_AMOUNT,
           MORTGAGE_PERIOD,
           INTEREST,
           RATE_TYPE
   );

   public static final List<FormattingFunction<InputData>> introDescribers = List.of(
           inputData -> inputData.getStartingAmount(),
           inputData -> inputData.getStartingDuration(),
           inputData -> inputData.getInterestDisplay(),
           inputData -> inputData.getRateType()
   );

   public static final List<String> introAppendixes = List.of(
           CURRENCY,
           MONTHS,
           PERCENT,
           ""
   );
}