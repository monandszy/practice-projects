package code.component.manageOrder.data;

import code.component.manageAccount.data.AccountJpaRepo;
import code.component.manageOrder.OrderDAO;
import code.component.manageOrder.domain.Order;
import code.component.manageOrder.domain.OrderEntity;
import code.component.manageOrder.domain.OrderPosition;
import code.component.manageOrder.domain.OrderPositionEntity;
import code.component.manageOrder.domain.mapper.OrderEntityMapper;
import code.component.manageRestaurant.data.jpa.MenuPositionJpaRepo;
import code.component.manageRestaurant.data.jpa.RestaurantJpaRepo;
import code.component.manageRestaurant.domain.MenuPositionEntity;
import code.component.manageRestaurant.manageDelivery.AddressJpaRepo;
import code.web.exception.DeliveryError;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class OrderRepo implements OrderDAO {

   private OrderJpaRepo orderJpaRepo;
   private OrderEntityMapper orderMapper;
   private OrderPositionJpaRepo orderPositionJpaRepo;

   private AddressJpaRepo addressJpaRepo;
   private AccountJpaRepo accountJpaRepo;
   private RestaurantJpaRepo restaurantJpaRepo;
   private MenuPositionJpaRepo menuPositionJpaRepo;

   @Override
   public Order getById(Integer orderId) {
      return orderMapper.mapFromEntity(orderJpaRepo.findById(orderId)
          .orElseThrow());
   }

   @Override
   public List<OrderPosition> getOrderPositions(Integer orderId) {
      return orderMapper.mapOPFromEntityList(orderPositionJpaRepo.findByOrderId(orderId));
   }

   @Override
   public Order add(Order order, Integer addressId, String sellerId, String clientId, Integer restaurantId) {
      OrderEntity entity = orderMapper.mapToEntity(order);
//      entity.setAddress(addressJpaRepo.findById(addressId).orElseThrow());
      entity.setClient(accountJpaRepo.findByUserName(clientId).orElseThrow());
      entity.setSeller(accountJpaRepo.findByUserName(sellerId).orElseThrow());
      entity.setRestaurant(restaurantJpaRepo.findById(restaurantId).orElseThrow());
      orderJpaRepo.save(entity);
      return orderMapper.mapFromEntity(entity);
   }

   @Override
   public void addOrderPositions(List<OrderPosition> orderPositions, List<Integer> selected, Integer orderId) {
      OrderEntity order = orderJpaRepo.findById(orderId).orElseThrow();
      List<OrderPositionEntity> entities = orderMapper.mapOPToEntityList(orderPositions);
      List<MenuPositionEntity> allById = menuPositionJpaRepo.findAllById(selected);
      if (allById.size() != selected.size()) throw new DeliveryError("Invalid MenuPosition selected");
      for (int i = 0; i < selected.size(); i++) {
         OrderPositionEntity entity = entities.get(i);
         entity.setMenuPosition(allById.get(i));
         entity.setOrder(order);
      }
      orderPositionJpaRepo.saveAll(entities);
   }

   @Override
   public void updateOrderStatus(Integer orderId, Order.OrderStatus status) {
      OrderEntity entity = orderJpaRepo.findById(orderId).orElseThrow();
      entity.setStatus(status);
   }

   @Override
   public List<Order> getIncompleteOrdersBySellerId(String sellerId) {
      return orderMapper.mapOFromEntityList(orderJpaRepo.
          findIncompleteBySellerUserName(sellerId));
   }

   @Override
   public List<Order> getCompleteOrdersBySellerId(String sellerId) {
      return orderMapper.mapOFromEntityList(orderJpaRepo.
          findCompleteBySellerUserName(sellerId));
   }

   @Override
   public List<Order> getOrdersByClientId(String clientId) {
      return orderMapper.mapOFromEntityList(orderJpaRepo.findByClientUserName(clientId));
   }
}