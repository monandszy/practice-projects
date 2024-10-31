package code.order;

import code.component.manageAccount.data.AccountRepo;
import code.component.manageAccount.domain.Account;
import code.component.manageAccount.domain.mapper.AccountEntityMapperImpl;
import code.component.manageOrder.data.OrderRepo;
import code.component.manageOrder.domain.Order;
import code.component.manageOrder.domain.OrderPosition;
import code.component.manageOrder.domain.mapper.OrderEntityMapperImpl;
import code.component.manageRestaurant.data.MenuPositionRepo;
import code.component.manageRestaurant.data.MenuRepo;
import code.component.manageRestaurant.data.RestaurantRepo;
import code.component.manageRestaurant.domain.Menu;
import code.component.manageRestaurant.domain.MenuPosition;
import code.component.manageRestaurant.domain.Restaurant;
import code.component.manageRestaurant.domain.mapper.RestaurantEntityMapperImpl;
import code.component.manageRestaurant.manageDelivery.AddressRepo;
import code.component.manageRestaurant.manageDelivery.domain.Address;
import code.component.manageRestaurant.manageDelivery.domain.AddressEntityMapperImpl;
import code.configuration.AbstractJpaIT;
import code.util.DataFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(value = {
    OrderRepo.class,
    RestaurantRepo.class,
    AddressRepo.class,
    MenuRepo.class,
    MenuPositionRepo.class,
    OrderEntityMapperImpl.class,
    AddressEntityMapperImpl.class,
    RestaurantEntityMapperImpl.class,
    AccountEntityMapperImpl.class,
    AccountRepo.class
})
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderRepoIT extends AbstractJpaIT {

   private OrderRepo orderRepo;
   private RestaurantRepo restaurantRepo;
   private MenuRepo menuRepo;
   private MenuPositionRepo menuPositionRepo;
   private AddressRepo addressRepo;
   private AccountRepo accountRepo;

   @Test
   void testGet() {
      Order order = testAddOrder();
      String sellerId = order.getSeller().getUserName();
      String clientId = order.getSeller().getUserName();
      testAddOrderPosition(order.getRestaurant().getId(), order);

      List<Order> ordersByClientId = orderRepo.getOrdersByClientId(clientId);
      List<Order> incompleteOrdersBySellerId = orderRepo.getIncompleteOrdersBySellerId(sellerId);
      Assertions.assertFalse(ordersByClientId.isEmpty());
      Assertions.assertFalse(incompleteOrdersBySellerId.isEmpty());

      Integer orderId = order.getId();
      Order orderById = orderRepo.getById(orderId);
      Assertions.assertEquals(order, orderById);

      List<OrderPosition> orderPositions = orderRepo.getOrderPositions(orderId);
      Assertions.assertFalse(orderPositions.isEmpty());
   }

   private Order testAddOrder() {
      Account account = DataFixtures.getAccount();
      String sellerId = account.getUserName();
      String clientId = account.getUserName();
      accountRepo.addAccount(account, DataFixtures.getSellerRole());
      Address address = addressRepo.addOrFindByIp(DataFixtures.getAddress());
      Restaurant restaurant = restaurantRepo.add(
          DataFixtures.getRestaurant(), address.getId(), sellerId);

      return orderRepo.add(DataFixtures.getOrder(),
          address.getId(), sellerId, clientId, restaurant.getId()
      ).withRestaurant(restaurant).withSeller(account).withClient(account);
   }

   private void testAddOrderPosition(int restaurantId, Order order) {
      Menu add = menuRepo.add(DataFixtures.getMenu(), restaurantId);
      MenuPosition add1 = menuPositionRepo.add(DataFixtures.getMenuPosition(), add.getId());
      orderRepo.addOrderPositions(List.of(DataFixtures.getOrderPosition()),
          List.of(add1.getId()), order.getId());
   }


   @Test
   void testUpdate() {
      Order order = testAddOrder();
      String sellerId = order.getSeller().getUserName();
      testAddOrderPosition(order.getRestaurant().getId(), order);
      orderRepo.updateOrderStatus(order.getId(), Order.OrderStatus.COMPLETED);
      List<Order> orderById = orderRepo.getCompleteOrdersBySellerId(sellerId);
      Assertions.assertEquals(order.getId(), orderById.getFirst().getId());
      Assertions.assertEquals(Order.OrderStatus.COMPLETED,
          orderById.getFirst().getStatus());
   }

}