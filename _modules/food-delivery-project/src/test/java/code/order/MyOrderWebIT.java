package code.order;

import code.component.manageAccount.AccountService;
import code.component.manageOrder.OrderService;
import code.component.manageOrder.domain.OrderDTO;
import code.component.manageOrder.domain.OrderPositionDTO;
import code.component.manageOrder.domain.mapper.OrderDTOMapper;
import code.component.manageOrder.web.MyOrderController;
import code.component.manageRestaurant.domain.mapper.RestaurantDTOMapper;
import code.component.manageRestaurant.manageDelivery.domain.Address;
import code.component.manageRestaurant.manageDelivery.domain.AddressDTOMapper;
import code.configuration.Constants;
import code.util.DataFixtures;
import code.util.WebFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static code.component.manageOrder.web.MyOrderController.MY_ORDER_ADD;
import static code.component.manageOrder.web.MyOrderController.MY_ORDER_DELETE;
import static code.component.manageOrder.web.MyOrderController.MY_ORDER_getByClient;
import static code.component.manageOrder.web.MyOrderController.MY_ORDER_getForClient;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = MyOrderController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MyOrderWebIT {

   private MockMvc mockMvc;

   @MockBean
   private OrderService orderService;
   @MockBean
   private OrderDTOMapper orderDTOMapper;
   @MockBean
   private RestaurantDTOMapper restaurantDTOMapper;
   @MockBean
   private AddressDTOMapper addressDTOMapper;
   @MockBean
   private AccountService accountService;


   @Test
   void testGetByClient() throws Exception {
      String userName = "client";
      List<OrderDTO> orders = List.of(OrderDTO.builder().id(1).build());
      Mockito.when(accountService.getAuthenticatedUserName()).thenReturn(userName);
      Mockito.when(orderDTOMapper.mapOToDTOList(any())).thenReturn(orders);
      mockMvc.perform(get(Constants.URL + MY_ORDER_getByClient))
          .andExpect(model().attribute("myOrders", orders))
          .andExpect(view().name("client/order/myOrders"));
      Mockito.verify(orderService).getOrdersByClientId(userName);
   }

   @Test
   void testGetForClient() throws Exception {
      Integer orderId = 1;
      List<OrderPositionDTO> orderPositions = List.of(WebFixtures.getOrderPosition()
          .withMenuPositionDTO(WebFixtures.getMenuPositionDTO()));
      Mockito.when(orderDTOMapper.mapOPToDTOList(any())).thenReturn(orderPositions);
      mockMvc.perform(get(Constants.URL + MY_ORDER_getForClient, orderId))
          .andExpect(model().attribute("orderPositions", orderPositions))
          .andExpect(view().name("client/order/myOrder"));
      Mockito.verify(orderService).getOrderPositions(orderId);
   }

   @Test
   void testAdd() throws Exception {
      Integer restaurantId = 1;
      Integer[] selected = new Integer[]{1};
      String userName = "test";
      String selectedString = "1";
      Address address = DataFixtures.getAddress();
      Mockito.when(accountService.getAuthenticatedUserName()).thenReturn(userName);
      mockMvc.perform(post(Constants.URL + MY_ORDER_ADD)
              .param("selectedPositions", selectedString)
              .sessionAttr(Constants.ADDRESS, address)
              .sessionAttr(Constants.RESTAURANT, restaurantId))
          .andExpect(redirectedUrl("/" + MY_ORDER_getByClient));
      Mockito.verify(orderService).addOrder(selected, userName, address, restaurantId);
   }

   @Test
   void testDelete() throws Exception {
      Integer orderId = 1;
      mockMvc.perform(post(Constants.URL + MY_ORDER_DELETE, orderId))
          .andExpect(redirectedUrl("/" + MY_ORDER_getByClient))
          .andExpect(model().hasNoErrors())
          .andExpect(status().isFound());
      Mockito.verify(orderService).cancelOrder(orderId);
   }
}