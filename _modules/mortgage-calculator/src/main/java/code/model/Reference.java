package code.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class Reference implements Serializable {

   private BigDecimal referenceAmount;
   private BigDecimal referenceDuration;
   private BigDecimal referenceInterestPercent;
   private BigDecimal finalDuration;

   public Reference(BigDecimal startingAmount, BigDecimal startingDuration, BigDecimal startingCalculationInterestPercent) {
      this.referenceAmount = startingAmount;
      this.referenceDuration = startingDuration;
      this.referenceInterestPercent = startingCalculationInterestPercent;
      this.finalDuration = startingDuration;
   }
}