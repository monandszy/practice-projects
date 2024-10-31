package code.business.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Value
@With
@Builder
@EqualsAndHashCode(of = {"catId"})
@ToString(of = {"catId", "name", "salary"})
public class Cat {

    Integer catId;
    String name;
    BigDecimal salary;
    ZonedDateTime happyDay;
}