package code.component.manageOrder.domain;

import code.component.manageAccount.domain.Account;
import code.component.manageRestaurant.domain.Restaurant;
import code.component.manageRestaurant.manageDelivery.domain.Address;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.With;

import java.time.OffsetDateTime;
import java.util.List;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id"})
public class Order {

   Integer id;
   OrderStatus status;
   OffsetDateTime timeOfOrder;
   List<OrderPosition> orderPositions;
   Account client;
   Account seller;
   Address address;
   Restaurant restaurant;

   public enum OrderStatus {
      IN_PROGRESS,
      TRAVELLING,
      CANCELLED,
      COMPLETED
   }

}