package code.component.manageOrder.domain;

import code.component.manageRestaurant.domain.MenuPosition;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.With;
@With
@Value
@Builder
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id"})
public class OrderPosition {

   Integer id;
   MenuPosition menuPosition;
}