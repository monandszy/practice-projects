package code.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor(staticName = "of")
@Setter
public class OverpaymentData implements Serializable {

   private final BigDecimal amount;
   private final OverpaymentType overpaymentType;
   private BigDecimal overpaymentCut = BigDecimal.ZERO;

}