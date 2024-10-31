package code.component.manageOrder.domain.mapper;

import code.component.manageOrder.domain.Order;
import code.component.manageOrder.domain.OrderEntity;
import code.component.manageOrder.domain.OrderPosition;
import code.component.manageOrder.domain.OrderPositionEntity;
import code.component.manageRestaurant.domain.mapper.RestaurantEntityMapperImpl;
import code.configuration.Generated;
import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@AnnotateWith(Generated.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
uses = RestaurantEntityMapperImpl.class)
public interface OrderEntityMapper {

   @Mapping(target = "orderPositions", source = "orderPositions", ignore = true)
   @Mapping(target = "client", source = "client", ignore = true)
   @Mapping(target = "seller", source = "seller", ignore = true)
   OrderEntity mapToEntity(Order order);

   @Mapping(target = "orderPositions", source = "orderPositions", ignore = true)
   @Mapping(target = "client", source = "client", ignore = true)
   @Mapping(target = "seller", source = "seller", ignore = true)
   Order mapFromEntity(OrderEntity orderEntity);

   OrderPositionEntity mapToEntity(OrderPosition orderPosition);

   OrderPosition mapFromEntity(OrderPositionEntity orderPositionEntity);
   
   List<OrderPosition> mapOPFromEntityList(List<OrderPositionEntity> orderPositions);
   List<OrderPositionEntity> mapOPToEntityList(List<OrderPosition> orderPositions);

   List<Order> mapOFromEntityList(List<OrderEntity> orderPositions);
}