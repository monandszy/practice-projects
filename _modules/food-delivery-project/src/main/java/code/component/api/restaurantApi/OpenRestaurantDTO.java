package code.component.api.restaurantApi;

import code.component.manageRestaurant.domain.MenuDTO;
import code.component.manageRestaurant.domain.MenuPositionDTO;
import code.component.manageRestaurant.domain.RestaurantDTO;
import code.component.manageRestaurant.manageDelivery.domain.AddressDTO;
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
public class OpenRestaurantDTO {

   private RestaurantDTO restaurant;
   private MenuDTO menu;
   private List<MenuPositionDTO> menuPositions;
   private AddressDTO address;
}