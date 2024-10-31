package code.component.manageRestaurant.domain.mapper;

import code.component.manageAccount.domain.Account;
import code.component.manageRestaurant.domain.Menu;
import code.component.manageRestaurant.domain.MenuDTO;
import code.component.manageRestaurant.domain.MenuPosition;
import code.component.manageRestaurant.domain.MenuPositionDTO;
import code.component.manageRestaurant.domain.Restaurant;
import code.component.manageRestaurant.domain.RestaurantDTO;
import code.component.manageRestaurant.manageDelivery.domain.Address;
import code.configuration.Generated;
import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Objects;

@AnnotateWith(Generated.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantDTOMapper {

   @Mapping(target = "sellerOutput", source = "seller", qualifiedByName = "sellerMapping")
   @Mapping(target = "addressOutput", source = "address", qualifiedByName = "addressMapping")
   RestaurantDTO mapToDTO(Restaurant restaurant);

   @Mapping(target = "menus", ignore = true)
   @Mapping(target = "seller", ignore = true)
   Restaurant mapFromDTO(RestaurantDTO restaurantDTO);

   MenuDTO mapToDTO(Menu menu);

   @Mapping(target = "menuPositions", ignore = true)
   @Mapping(target = "restaurant", ignore = true)
   Menu mapFromDTO(MenuDTO menuDTO);

   MenuPositionDTO mapToDTO(MenuPosition menuPosition);

   @Mapping(target = "menu", ignore = true)
   MenuPosition mapFromDTO(MenuPositionDTO menuPositionDTO);

   @Named("sellerMapping")
   default String sellerMapping(Account account) {
      if (Objects.isNull(account)) return null;
      return account.getUserName();
   }

   @Named("addressMapping")
   default String addressMapping(Address address) {
      if (Objects.isNull(address)) return null;
      return address.toString();
   }

   List<RestaurantDTO> mapRToDTOList(List<Restaurant> restaurants);
   List<Restaurant> mapRFromDTOList(List<RestaurantDTO> restaurantDTOs);

   List<MenuDTO> mapMToDTOList(List<Menu> menus);
   List<Menu> mapMFromDTOList(List<MenuDTO> menuDTOs);

   List<MenuPositionDTO> mapMPToDTOList(List<MenuPosition> menuPositions);
   List<MenuPosition> mapMPFromDTOList(List<MenuPositionDTO> menuPositionDTOs);
}