package code.model.rate;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class TimePoint implements Serializable {

   LocalDate date;
   BigDecimal month;
   BigDecimal year;
   Boolean hasOverpayment;
   BigDecimal rateNumber;
}