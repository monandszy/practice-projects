package code.model.rate;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static code.model.InputData.Scales.DISPLAYING_SCALE;
import static code.model.InputData.Scales.ROUNDING_SCALE;


@Value
public class MortgageResidual implements Serializable {
   BigDecimal residualAmount;
   BigDecimal residualDuration;

   public BigDecimal getResidualAmount() {
      return residualAmount.setScale(ROUNDING_SCALE, RoundingMode.HALF_UP);
   }

   public BigDecimal getResidualAmountDisplay() {
      return residualAmount.setScale(DISPLAYING_SCALE, RoundingMode.HALF_UP);
   }

   public BigDecimal getResidualDurationDisplay() {
      return residualDuration.setScale(0, RoundingMode.DOWN);
   }

   public BigDecimal getResidualDuration() {
      return residualDuration.setScale(ROUNDING_SCALE, RoundingMode.HALF_UP);
   }

}