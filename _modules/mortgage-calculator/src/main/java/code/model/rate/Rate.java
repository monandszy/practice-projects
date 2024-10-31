package code.model.rate;


import lombok.Value;

import java.io.Serializable;

@Value
public class Rate implements Serializable {

   TimePoint timePoint;
   RateAmounts rateAmounts;
   MortgageResidual mortgageResidual;
}