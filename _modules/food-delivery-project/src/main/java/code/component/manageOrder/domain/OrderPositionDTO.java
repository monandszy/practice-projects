package code.component.manageOrder.domain;

import code.component.manageRestaurant.domain.MenuPositionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPositionDTO {

   private Integer id;
   private MenuPositionDTO menuPositionDTO;
}