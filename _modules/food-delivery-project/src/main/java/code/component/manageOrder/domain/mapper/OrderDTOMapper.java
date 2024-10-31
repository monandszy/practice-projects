package code.component.manageOrder.domain.mapper;

import code.component.manageOrder.domain.Order;
import code.component.manageOrder.domain.OrderDTO;
import code.component.manageOrder.domain.OrderPosition;
import code.component.manageOrder.domain.OrderPositionDTO;
import code.component.manageRestaurant.domain.Restaurant;
import code.component.manageRestaurant.domain.mapper.RestaurantDTOMapperImpl;
import code.configuration.Generated;
import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@AnnotateWith(Generated.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = RestaurantDTOMapperImpl.class)
public interface OrderDTOMapper {

   @Mapping(target = "addressOutput", source = "address", qualifiedByName = "addressMapping")
   @Mapping(target = "restaurantId", source = "restaurant", qualifiedByName = "restaurantMapping")
   @Mapping(target = "timeOfOrder", source = "timeOfOrder", qualifiedByName = "timeToStringMapping")
   OrderDTO mapToDTO(Order order);

   @Mapping(target = "orderPositions", ignore = true)
   @Mapping(target = "client", ignore = true)
   @Mapping(target = "timeOfOrder", source = "timeOfOrder", qualifiedByName = "stringToTimeMapping")
   Order mapFromDTO(OrderDTO orderDTO);

   @Mapping(target = "menuPositionDTO", source = "menuPosition")
   OrderPositionDTO mapToDTO(OrderPosition orderPosition);

   @Mapping(target = "menuPosition", ignore = true)
   OrderPosition mapFromDTO(OrderPositionDTO orderPositionDTO);

   @Named("restaurantMapping")
   default Integer restaurantMapping(Restaurant restaurant) {
      if (Objects.isNull(restaurant)) return null;
      return restaurant.getId();
   }

   @Named("timeToStringMapping")
   default String timeToStringMapping(OffsetDateTime offsetDateTime) {
      return offsetDateTime.toString();
   }

   @Named("stringToTimeMapping")
   default OffsetDateTime stringToTimeMapping(String time) {
      return OffsetDateTime.parse(time);
   }

   List<OrderPositionDTO> mapOPToDTOList(List<OrderPosition> orderPositions);

   List<OrderDTO> mapOToDTOList(List<Order> orderPositions);
}