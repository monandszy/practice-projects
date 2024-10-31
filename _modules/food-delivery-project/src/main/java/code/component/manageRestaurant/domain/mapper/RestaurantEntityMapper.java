package code.component.manageRestaurant.domain.mapper;

import code.component.manageRestaurant.domain.Menu;
import code.component.manageRestaurant.domain.MenuEntity;
import code.component.manageRestaurant.domain.MenuPosition;
import code.component.manageRestaurant.domain.MenuPositionEntity;
import code.component.manageRestaurant.domain.Restaurant;
import code.component.manageRestaurant.domain.RestaurantEntity;
import code.component.manageRestaurant.manageImages.ImageIdEntity;
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
public interface RestaurantEntityMapper {
   @Mapping(target = "menus", source = "menus", ignore = true)
   RestaurantEntity mapToEntity(Restaurant restaurant);

   @Mapping(target = "menus", source = "menus", ignore = true)
   Restaurant mapFromEntity(RestaurantEntity restaurantEntity);

//   @Mapping(target = "menuPositions", source = "menuPositions", ignore = true)
   @Mapping(target = "restaurant", source = "restaurant", ignore = true)
   MenuEntity mapToEntity(Menu menu);

//   @Mapping(target = "menuPositions", source = "menuPositions", ignore = true)
   @Mapping(target = "restaurant", source = "restaurant", ignore = true)
   Menu mapFromEntity(MenuEntity menuEntity);

   @Mapping(target = "images", source = "images", ignore = true)
   @Mapping(target = "menu", source = "menu", ignore = true)
   MenuPositionEntity mapToEntity(MenuPosition menuPosition);

   @Mapping(target = "menu", source = "menu", ignore = true)
   @Mapping(target = "images", source = "images", qualifiedByName = "imageMapping")
   MenuPosition mapFromEntity(MenuPositionEntity menuPositionEntity);

   List<Restaurant> mapRFromEntityList(List<RestaurantEntity> all);
   List<RestaurantEntity> mapRToEntityList(List<Restaurant> all);

   @Named("imageMapping")
   default List<Integer> imageMapping(List<ImageIdEntity> images) {
      if (Objects.isNull(images)) return null;
      return images.stream().map(ImageIdEntity::getId).toList();
   }

}