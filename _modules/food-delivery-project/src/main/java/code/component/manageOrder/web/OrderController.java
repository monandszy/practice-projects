package code.component.manageOrder.web;

import code.component.manageAccount.AccountService;
import code.component.manageOrder.OrderService;
import code.component.manageOrder.domain.OrderDTO;
import code.component.manageOrder.domain.OrderPositionDTO;
import code.component.manageOrder.domain.mapper.OrderDTOMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class OrderController {

   public static final String ORDER = "order";
   public static final String ORDER_getBySeller = ORDER + "/getBySeller";
   public static final String ORDER_getForSeller = ORDER + "/getForSeller/{orderId}";
   public static final String ORDER_COMPLETE = ORDER + "/complete/{orderId}";

   private OrderService orderService;
   private OrderDTOMapper dtoMapper;
   private AccountService accountService;

   @GetMapping(value = ORDER_getBySeller)
   public String getIncompleteOrdersBySellerId(
       Model model
   ) {
      String sellerId = accountService.getAuthenticatedUserName();
      List<OrderDTO> incomplete = dtoMapper.mapOToDTOList(
          orderService.getIncompleteOrdersBySellerId(sellerId));
      List<OrderDTO> complete = dtoMapper.mapOToDTOList(
          orderService.getCompleteOrdersBySellerId(sellerId));
      model.addAttribute("incompleteOrders", incomplete);
      model.addAttribute("completeOrders", complete);
      return "seller/order/orders";
   }

   @GetMapping(value = ORDER_getForSeller)
   public String getOrderPositionsForSeller(
       @PathVariable Integer orderId,
       Model model
   ) {
      List<OrderPositionDTO> orderPositions = dtoMapper.mapOPToDTOList(
          orderService.getOrderPositions(orderId));
      model.addAttribute("orderPositions", orderPositions);
      return "seller/order/order";
   }

   @PostMapping(value = ORDER_COMPLETE)
   public String completeOrder(
       @PathVariable("orderId") Integer orderId
   ) {
      orderService.complete(orderId);
      return "redirect:/order/getBySeller";
   }
}