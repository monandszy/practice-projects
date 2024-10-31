package code.model.rate;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Value
public class RateAmounts implements Serializable {

   BigDecimal rateAmount;
   BigDecimal interestAmount;
   BigDecimal capitalAmount;

   public BigDecimal getRateAmount() {
      return rateAmount.setScale(10, RoundingMode.HALF_UP);
   }

   public BigDecimal getInterestAmount() {
      return interestAmount.setScale(10, RoundingMode.HALF_UP);
   }

   public BigDecimal getCapitalAmount() {
      return capitalAmount.setScale(10, RoundingMode.HALF_UP);
   }

   public BigDecimal getRateAmountDisplay() {
      return rateAmount.setScale(2, RoundingMode.HALF_UP);
   }

   public BigDecimal getInterestAmountDisplay() {
      return interestAmount.setScale(2, RoundingMode.HALF_UP);
   }

   public BigDecimal getCapitalAmountDisplay() {
      return capitalAmount.setScale(2, RoundingMode.HALF_UP);
   }

}