package code.component.manageOrder.web;

import code.component.manageAccount.AccountService;
import code.component.manageOrder.OrderService;
import code.component.manageOrder.domain.OrderDTO;
import code.component.manageOrder.domain.OrderPositionDTO;
import code.component.manageOrder.domain.mapper.OrderDTOMapper;
import code.component.manageRestaurant.manageDelivery.domain.Address;
import code.configuration.Constants;
import code.web.exception.DeliveryError;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class MyOrderController {

   public static final String MY_ORDER = "myOrder";
   public static final String MY_ORDER_getByClient = MY_ORDER + "/getByClient";
   public static final String MY_ORDER_getForClient = MY_ORDER + "/getForClient/{orderId}";
   public static final String MY_ORDER_ADD = MY_ORDER + "/add";
   public static final String MY_ORDER_DELETE = MY_ORDER + "/delete/{orderId}";

   private OrderService orderService;
   private OrderDTOMapper orderDTOMapper;
   private AccountService accountService;

   @GetMapping(value = MY_ORDER_getByClient)
   public String getOrdersByClientId(
       Model model
   ) {
      String clientId = accountService.getAuthenticatedUserName();
      List<OrderDTO> orders = orderDTOMapper.
          mapOToDTOList(orderService.getOrdersByClientId(clientId));
      model.addAttribute("myOrders", orders);
      return "client/order/myOrders";
   }

   @GetMapping(value = MY_ORDER_getForClient)
   public String getOrderPositionsForClient(
       @PathVariable Integer orderId,
       Model model
   ) {
      List<OrderPositionDTO> orderPositions = orderDTOMapper.
          mapOPToDTOList(orderService.getOrderPositions(orderId));
      model.addAttribute("orderPositions", orderPositions);
      return "client/order/myOrder";
   }

   @PostMapping(value = MY_ORDER_ADD)
   public String postOrder(
       @RequestParam("selectedPositions") @Valid Integer[] selected,
       HttpSession session
   ) {
      if (selected.length == 0) throw new DeliveryError(
          "Your can't order nothing, pick an order Position before proceeding");
      Address address = (Address) session.getAttribute(Constants.ADDRESS);
      Integer restaurantId = (Integer) session.getAttribute(Constants.RESTAURANT);
      orderService.addOrder(selected, accountService.getAuthenticatedUserName(),
          address, restaurantId
      );
      return "redirect:/myOrder/getByClient";
   }

   @PostMapping(value = MY_ORDER_DELETE)
   public String deleteOrder(
       @PathVariable("orderId") Integer orderId
   ) {
      orderService.cancelOrder(orderId);
      return "redirect:/myOrder/getByClient";
   }
}