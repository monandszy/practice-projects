package code.component.api.restaurantApi;

import code.component.manageAccount.AccountService;
import code.component.manageRestaurant.domain.MenuDTO;
import code.component.manageRestaurant.domain.MenuPositionDTO;
import code.component.manageRestaurant.domain.RestaurantDTO;
import code.component.manageRestaurant.domain.mapper.RestaurantDTOMapper;
import code.component.manageRestaurant.manageDelivery.domain.AddressDTOMapper;
import code.component.manageRestaurant.service.MenuPositionService;
import code.component.manageRestaurant.service.MenuService;
import code.component.manageRestaurant.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class RestaurantRestController {

   public static final String API_RESTAURANT = "api/restaurant";
   public static final String RESTAURANT_OPEN = API_RESTAURANT + "/open";

   private RestaurantDTOMapper restaurantDTOMapper;
   private AddressDTOMapper addressDTOMapper;
   private RestaurantService restaurantService;
   private MenuService menuService;
   private MenuPositionService menuPositionService;
   private AccountService accountService;

   @PostMapping(value = RESTAURANT_OPEN, produces = MediaType.APPLICATION_JSON_VALUE)
   public OpenRestaurantDTO openRestaurant(
       @RequestBody @Valid OpenRestaurantDTO restaurantDTO
   ) {
      String sellerId = accountService.getAuthenticatedUserName();
      Integer restaurantId = addRestaurant(restaurantDTO, sellerId);
      Integer menuId = addMenu(restaurantDTO, restaurantId);
      List<Integer> integers = addMenuPositions(restaurantDTO, menuId);
      return OpenRestaurantDTO.builder()
          .restaurant(RestaurantDTO.builder().id(restaurantId).build())
          .menu(MenuDTO.builder().id(menuId).build())
          .menuPositions(integers.stream().map(e -> MenuPositionDTO.builder().id(e).build()).toList())
          .build();
   }

   private Integer addMenu(OpenRestaurantDTO restaurantDTO, Integer id) {
      return menuService.add(restaurantDTOMapper.mapFromDTO(restaurantDTO.getMenu()), id);
   }

   private List<Integer> addMenuPositions(OpenRestaurantDTO restaurantDTO, Integer menuId) {
      return restaurantDTOMapper.mapMPFromDTOList(restaurantDTO.getMenuPositions()).
          stream().map(e -> menuPositionService.add(null, e, menuId)).toList();
   }

   private Integer addRestaurant(
       OpenRestaurantDTO restaurantDTO,
       String sellerId
   ) {
      return restaurantService.add(restaurantDTOMapper
              .mapFromDTO(restaurantDTO.getRestaurant()),
          addressDTOMapper.mapFromDTO(restaurantDTO.getAddress()),
          sellerId);
   }
}