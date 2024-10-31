package code.component.api.orderApi;

import code.component.manageAccount.AccountService;
import code.component.manageOrder.OrderService;
import code.component.manageOrder.domain.Order;
import code.component.manageOrder.domain.OrderPosition;
import code.component.manageOrder.domain.mapper.OrderDTOMapper;
import code.component.manageRestaurant.manageDelivery.domain.AddressDTOMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
public class OrderRestController {

   public static final String API_ORDER = "api/order";

   public static final String ORDER_GET = API_ORDER + "/get/{clientId}";
   public static final String ORDER_GET_DETAILS = API_ORDER + "/details/get/{orderId}";
   public static final String ORDER_ADD = API_ORDER + "/add";
   public static final String ORDER_DELETE = API_ORDER + "/delete/{orderId}";

   private OrderService orderService;
   private AccountService accountService;
   private AddressDTOMapper addressDTOMapper;
   private OrderDTOMapper orderDTOMapper;

   @GetMapping(value = ORDER_GET, produces = MediaType.APPLICATION_JSON_VALUE)
   public OrderDTOs getOrders(
       @PathVariable String clientId
   ) {
      List<Order> ordersByClientId = orderService.getOrdersByClientId(clientId);
      return OrderDTOs.builder().orders(orderDTOMapper.mapOToDTOList(ordersByClientId)).build();
   }

   @GetMapping(value = ORDER_GET_DETAILS, produces = MediaType.APPLICATION_JSON_VALUE)
   public OrderPositionDTOs getOrderDetails(
       @PathVariable Integer orderId
   ) {
      List<OrderPosition> orderPositions = orderService.getOrderPositions(orderId);
      return OrderPositionDTOs.builder().orderPositions(orderDTOMapper.mapOPToDTOList(orderPositions)).build();
   }

   @PostMapping(value = ORDER_ADD)
   public ResponseEntity<?> addOrder(
       @RequestBody @Valid OrderInputDTO orderInputDTO
   ) {
      String clientId = accountService.getAuthenticatedUserName();
      Order order = orderService.addOrder(orderInputDTO.getSelected(), clientId,
          addressDTOMapper.mapFromDTO(orderInputDTO.getAddressDTO()), orderInputDTO.getRestaurantId());
      return ResponseEntity.created(URI.create(ORDER_GET_DETAILS.replace("{orderId}", order.getId().toString())))
          .build();
   }

   @DeleteMapping(value = ORDER_DELETE)
   public ResponseEntity<?> cancelOrder(
       @PathVariable Integer orderId
   ) {
      orderService.cancelOrder(orderId);
      return ResponseEntity.ok()
          .build();
   }
}