package code.component.api.orderApi;

import code.component.manageOrder.domain.OrderDTO;
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
public class OrderDTOs {

   private List<OrderDTO> orders;
}