package code.api.restAssured;

import code.component.api.orderApi.OrderDTOs;
import code.component.api.orderApi.OrderInputDTO;
import code.component.api.orderApi.OrderPositionDTOs;
import code.component.api.restaurantApi.OpenRestaurantDTO;
import code.component.manageAccount.data.AccountJpaRepo;
import code.component.manageAccount.domain.AccountDTO;
import code.component.manageOrder.data.OrderJpaRepo;
import code.component.manageOrder.domain.Order;
import code.component.manageRestaurant.data.jpa.RestaurantJpaRepo;
import code.component.manageRestaurant.manageDelivery.AddressJpaRepo;
import code.configuration.RestAssuredITBase;
import code.configuration.WireMockTestSupport;
import code.util.ApiFixtures;
import code.util.WebFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiIT extends RestAssuredITBase
    implements OrderControllerSupport,
    RestaurantControllerSupport,
    AccountControllerSupport,
    WireMockTestSupport {

   private AccountJpaRepo accountJpaRepo;
   private RestaurantJpaRepo restaurantJpaRepo;
   private OrderJpaRepo orderJpaRepo;
   private AddressJpaRepo addressJpaRepo;

   @Test
      // if not present - some kind of error - throws NotFound
   void testApi() throws Exception {
      AccountDTO account = WebFixtures.getAccount();
      String userName = account.getUserName();
      register(account);

      OpenRestaurantDTO openRestaurantDTO = openRestaurant(ApiFixtures.getOpenRestaurant());

      Integer[] selected = openRestaurantDTO.getMenuPositions()
          .stream().map(e -> e.getId()).toArray(Integer[]::new);
      OrderInputDTO newOrder = OrderInputDTO.builder()
          .addressDTO(WebFixtures.getAddress())
          .restaurantId(openRestaurantDTO.getRestaurant().getId())
          .selected(selected)
          .build();
      addOrder(newOrder);

      OrderDTOs orders = getOrders(userName);
      Integer id = orders.getOrders().getFirst().getId();
      OrderPositionDTOs orderDetails = getOrderDetails(id);

      cancelOrder(id);
      OrderDTOs cancelled = getOrders(userName);
      org.assertj.core.api.Assertions.assertThat(cancelled.getOrders().getFirst().getStatus()
          ).isEqualTo(Order.OrderStatus.CANCELLED);

      orderJpaRepo.deleteAll();
      restaurantJpaRepo.deleteAll();
      addressJpaRepo.deleteAll();
      accountJpaRepo.deleteAll();
   }
}