package code.util;

import code.component.api.restaurantApi.OpenRestaurantDTO;

import java.util.List;

public class ApiFixtures {
   public static OpenRestaurantDTO getOpenRestaurant() {
      return OpenRestaurantDTO.builder()
          .restaurant(WebFixtures.getRestaurantDTO())
          .menu(WebFixtures.getMenuDTO())
          .menuPositions(List.of(WebFixtures.getMenuPositionDTO()))
          .address(WebFixtures.getAddress())
          .build();
   }
}