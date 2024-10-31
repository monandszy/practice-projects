package code.component.manageOrder;

import code.component.manageOrder.domain.Order;
import code.component.manageOrder.domain.OrderPosition;

import java.util.List;

public interface OrderDAO {

   List<OrderPosition> getOrderPositions(Integer orderId);

   Order add(Order order, Integer addressId, String sellerId, String clientId, Integer restaurantId);

   void addOrderPositions(List<OrderPosition> orderPositions, List<Integer> selected, Integer orderId);

   void updateOrderStatus(Integer orderId, Order.OrderStatus updatedStatus);

   List<Order> getIncompleteOrdersBySellerId(String sellerId);

   List<Order> getOrdersByClientId(String clientId);

   Order getById(Integer orderId);

   public List<Order> getCompleteOrdersBySellerId(String sellerId);
}