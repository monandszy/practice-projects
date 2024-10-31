package code.service.printing;

import code.model.FormattingValues;
import code.model.InputData;
import code.model.OverpaymentData;
import code.model.Summary;
import code.model.rate.Rate;
import code.service.printing.output.OutputServiceI;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static code.model.FormattingValues.introAppendixes;
import static code.model.FormattingValues.introLabels;
import static code.model.FormattingValues.overpaymentDescribers;
import static code.model.FormattingValues.overpaymentKeys;
import static code.model.FormattingValues.rateDescribers;
import static code.model.FormattingValues.rateKeys;
import static code.model.FormattingValues.summaryAppendixes;
import static code.model.FormattingValues.summaryLabels;

@Slf4j
@Value
@Service
public class PrintingService implements PrintingServiceI {

   TableFormattingServiceI tableFormatterI;
   OutputServiceI outputServiceI;


   public PrintingService(TableFormattingServiceI tableFormatterI, @Qualifier("outputServiceI") OutputServiceI outputServiceI) {
      this.tableFormatterI = tableFormatterI;
      this.outputServiceI = outputServiceI;
   }

   @Override
   public void printData(InputData inputData, List<Rate> rates, Summary summary) {
      Format rateFormat = tableFormatterI.KeyValueFormatter(rateKeys, rates, rateDescribers);
      Format overpaymentFormat = tableFormatterI.KeyValueFormatter(
              overpaymentKeys, inputData.getOverpaymentMap().values(), overpaymentDescribers);
      log.info("PrintingData Start");
      saveText("------------------------------ MORTGAGE CALCULATOR ------------------------------");
      printIntro(inputData);
      if (inputData.isDoCalculateSummary()) printSummary(summary);
      printOverpaymentMap(inputData, overpaymentFormat);
      printRates(rates, inputData, rateFormat, overpaymentFormat);
      log.info("PrintingData End");
   }

   @Override
   public void printIntro(InputData inputData) {
      saveText("------------------------------ INPUT INTRO ------------------------------");
      List<Object> introValues = FormattingUtils.functionsToObjects(FormattingValues.introDescribers, inputData);
      Format format = tableFormatterI.multiCollectionFormatter(introLabels, introValues, introAppendixes);
      for (int i = 0; i < introLabels.size(); i++) {
         StringBuilder formattedRow = tableFormatterI.getRowFormattingService()
                 .getMultiCollectionFormattedRow(format, i, introLabels, introValues, introAppendixes);
         saveText(formattedRow.append("|"));
      }
   }

   @Override
   public void printOverpaymentMap(InputData inputData, Format overpaymentFormat) {
      Set<BigDecimal> overpaymentRateNumbers = inputData.getOverpaymentMap().keySet();
      saveText("------------------------------ OVERPAYMENT DATA MAP ------------------------------");
      for (BigDecimal rateNumber : overpaymentRateNumbers) {
         OverpaymentData overpaymentData = inputData.getOverpaymentMap().get(rateNumber);
         StringBuilder overpayment = getOverpaymentRow(overpaymentData, overpaymentFormat);
         saveText(overpayment.append("|"));
      }
   }

   @Override
   public void printRates(List<Rate> rates, InputData inputData, Format rateFormat, Format overpaymentFormat) {
      saveText("------------------------------ RATES ------------------------------");
      BigDecimal previousYear = BigDecimal.ZERO;
      for (Rate rate : rates) {
         if (inputData.isDoAddYearSeparator()) {
            BigDecimal currentYear = rate.getTimePoint().getYear();
            if (!previousYear.equals(currentYear)) {
               previousYear = currentYear;
               saveText(SEPARATOR);
            }
         }
         StringBuilder formattedRow = getRateRow(rateFormat, rate);
         BigDecimal rateNumber = rate.getTimePoint().getRateNumber();
         if (rate.getTimePoint().getHasOverpayment()) {
            OverpaymentData overpaymentData = inputData.getOverpaymentMap().get(rateNumber);
            StringBuilder overpayment = getOverpaymentRow(overpaymentData, overpaymentFormat);
            saveText(formattedRow.append(overpayment).append("|"));
         } else {
            saveText(formattedRow.append("|"));
         }
      }
   }

   private StringBuilder getRateRow(Format rateFormat, Rate rate) {
      return tableFormatterI.getRowFormattingService().getKeyValueFormattedRow(
              rateFormat, rateKeys, rate, rateDescribers);
   }

   private StringBuilder getOverpaymentRow(OverpaymentData overpaymentData, Format overpaymentFormat) {
      return tableFormatterI.getRowFormattingService().getKeyValueFormattedRow(
              overpaymentFormat, overpaymentKeys, overpaymentData, overpaymentDescribers);
   }

   @Override
   public void printSummary(Summary summary) {
      saveText("------------------------------ SUMMARY ------------------------------");
      List<Object> summaryValues = FormattingUtils.functionsToObjects(FormattingValues.summaryDescribers, summary);
      Format format = tableFormatterI.multiCollectionFormatter(summaryLabels, summaryValues, summaryAppendixes);
      for (int i = 0; i < summaryLabels.size(); i++) {
         StringBuilder formattedRow = tableFormatterI.getRowFormattingService()
                 .getMultiCollectionFormattedRow(format, i, summaryLabels, summaryValues, summaryAppendixes);
         saveText(formattedRow.append("|"));
      }
   }

   private void saveText(StringBuilder message) {
      saveText(message.toString());
   }

   private void saveText(String message) {
      outputServiceI.save(message);
   }

}