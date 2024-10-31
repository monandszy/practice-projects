package code.component.api.orderApi;

import code.component.manageOrder.domain.OrderPositionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPositionDTOs {

   private List<OrderPositionDTO> orderPositions;
}