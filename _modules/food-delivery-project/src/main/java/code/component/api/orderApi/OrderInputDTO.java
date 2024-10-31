package code.component.api.orderApi;

import code.component.manageRestaurant.manageDelivery.domain.AddressDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class OrderInputDTO {

   private AddressDTO addressDTO;
   @NotNull
   private Integer restaurantId;
   @NotEmpty
   private Integer[] selected;
}