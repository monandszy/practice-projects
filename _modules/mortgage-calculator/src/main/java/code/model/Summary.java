package code.model;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static code.model.InputData.Scales.DISPLAYING_SCALE;


@Value
public class Summary implements Serializable {

   BigDecimal interestSum;
   BigDecimal overpaymentProvisionSum;
   BigDecimal totalLoss;

   public BigDecimal getInterestSumDisplay() {
      return interestSum.setScale(DISPLAYING_SCALE, RoundingMode.HALF_UP);
   }

   public BigDecimal getOverpaymentProvisionSumDisplay() {
      return overpaymentProvisionSum.setScale(DISPLAYING_SCALE, RoundingMode.HALF_UP);
   }

   public BigDecimal getTotalLossDisplay() {
      return totalLoss.setScale(DISPLAYING_SCALE, RoundingMode.HALF_UP);
   }
}