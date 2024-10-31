package code.component.manageOrder.domain;

import code.component.manageAccount.domain.AccountEntity;
import code.component.manageRestaurant.domain.RestaurantEntity;
import code.component.manageRestaurant.manageDelivery.domain.AddressEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client_order", schema = "food_delivery")
public class OrderEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Integer id;

   @Enumerated(EnumType.STRING)
   @Column(name = "status")
   private Order.OrderStatus status;

   @Column(name = "time_of_order")
   private OffsetDateTime timeOfOrder;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "client_id")
   private AccountEntity client;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "seller_id")
   private AccountEntity seller;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "address_id")
   private AddressEntity address;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "restaurant_id")
   private RestaurantEntity restaurant;

   @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = {CascadeType.REMOVE})
   private List<OrderPositionEntity> orderPositions;
}