package code.component.manageRestaurant.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;
import java.util.List;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id"})
public class MenuPosition {

   Integer id;
   BigDecimal price;
   String name;
   Menu menu;
   List<Integer> images;
}