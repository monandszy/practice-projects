package code.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;
import lombok.With;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

import static code.model.InputData.Scales.ROUNDING_SCALE;

@Value
@Builder
@Getter
@ToString
public class InputData implements Serializable {
   public static final BigDecimal PERCENT = BigDecimal.valueOf(100);

   @With
   Reference reference;
   @With
   LocalDate repaymentStartDate;
   BigDecimal startingAmount;
   BigDecimal startingDuration;
   BigDecimal interestPercent;
   @With
   RateType rateType;
   @With
   BigDecimal overpaymentProvisionPercent;
   @With
   BigDecimal overpaymentProvisionMonths;
   @With
   Map<BigDecimal, OverpaymentData> overpaymentMap;
   @With
   boolean doPrint;
   @With
   boolean doSerialize;
   @With
   BigDecimal calculationCycle;
   @With
   boolean doAddYearSeparator;
   @With
   boolean doCalculateSummary;

   @Setter
   public static class Scales {
      public static Integer ROUNDING_SCALE = 10;
      public static Integer DISPLAYING_SCALE = 2;
   }

   public static InputData defaultInputData() {
      BigDecimal defaultStartingAmount = BigDecimal.valueOf(0);
      BigDecimal defaultInterestPercent = BigDecimal.valueOf(0);
      BigDecimal defaultStartingDuration = BigDecimal.valueOf(0);
      return InputData.builder()
              .repaymentStartDate(LocalDate.of(0, 1, 1))
              .startingAmount(defaultStartingAmount)
              .startingDuration(defaultStartingDuration)
              .interestPercent(defaultInterestPercent) // TODO bankMargin and WIBOR
              .reference(new Reference(
                      defaultStartingAmount,
                      defaultStartingDuration,
                      defaultInterestPercent.divide(PERCENT, ROUNDING_SCALE, RoundingMode.HALF_UP)
              ))
              .rateType(RateType.CONSTANT)
              .overpaymentProvisionPercent(BigDecimal.valueOf(0))
              .overpaymentProvisionMonths(BigDecimal.valueOf(0))
              .overpaymentMap(new TreeMap<>(Map.of()))
              .doPrint(false)
              .doSerialize(false)
              .calculationCycle(BigDecimal.valueOf(1)) // 1 -> all rates 13 -> none | pass if (year/cC)=0
              .doAddYearSeparator(false)
              .doCalculateSummary(false)
              .build();
   }

   public OverpaymentType getOverpaymentType(BigDecimal rateNumber) {
      return this.getOverpaymentMap().get(rateNumber).getOverpaymentType();
   }

   public BigDecimal getOverpaymentAmount(BigDecimal rateNumber) {
      return this.getOverpaymentMap().get(rateNumber).getAmount();
   }

   public BigDecimal getInterestPercent() {
      return interestPercent.divide(PERCENT, ROUNDING_SCALE, RoundingMode.HALF_UP);
   }

   public BigDecimal getInterestDisplay() {
      return interestPercent;
   }

   public BigDecimal getOverpaymentProvisionPercent() {
      return overpaymentProvisionPercent.divide(PERCENT, ROUNDING_SCALE, RoundingMode.HALF_UP);
   }

   // Modified @With to also update reference amounts

   public InputData withStartingAmount(BigDecimal startingAmount) {
      this.reference.setReferenceAmount(startingAmount);
      return this.startingAmount.equals(startingAmount) ? this : new InputData(this.reference, this.repaymentStartDate, startingAmount, this.startingDuration, this.interestPercent, this.rateType, this.overpaymentProvisionPercent, this.overpaymentProvisionMonths, this.overpaymentMap, this.doPrint, this.doSerialize, this.calculationCycle, this.doAddYearSeparator, this.doCalculateSummary);
   }

   public InputData withStartingDuration(BigDecimal startingDuration) {
      this.reference.setReferenceDuration(startingDuration);
      this.reference.setFinalDuration(startingDuration);
      return this.startingDuration.equals(startingDuration) ? this : new InputData(this.reference, this.repaymentStartDate, this.startingAmount, startingDuration, this.interestPercent, this.rateType, this.overpaymentProvisionPercent, this.overpaymentProvisionMonths, this.overpaymentMap, this.doPrint, this.doSerialize, this.calculationCycle, this.doAddYearSeparator, this.doCalculateSummary);
   }

   public InputData withInterestPercent(BigDecimal interestPercent) {
      this.reference.setReferenceInterestPercent(interestPercent.divide(PERCENT, ROUNDING_SCALE, RoundingMode.HALF_UP));
      return this.interestPercent.equals(interestPercent) ? this : new InputData(this.reference, this.repaymentStartDate, this.startingAmount, this.startingDuration, interestPercent, this.rateType, this.overpaymentProvisionPercent, this.overpaymentProvisionMonths, this.overpaymentMap, this.doPrint, this.doSerialize, this.calculationCycle, this.doAddYearSeparator, this.doCalculateSummary);
   }


}