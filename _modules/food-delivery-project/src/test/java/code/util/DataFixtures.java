package code.util;

import code.component.manageAccount.domain.Account;
import code.component.manageAccount.domain.Role;
import code.component.manageOrder.domain.Order;
import code.component.manageOrder.domain.OrderPosition;
import code.component.manageRestaurant.domain.Menu;
import code.component.manageRestaurant.domain.MenuPosition;
import code.component.manageRestaurant.domain.Restaurant;
import code.component.manageRestaurant.manageDelivery.domain.Address;
import code.component.manageRestaurant.manageImages.ImageEntity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;

public class DataFixtures {
   public static Restaurant getRestaurant() {
      return Restaurant.builder()
          .deliveryRange(1D)
          .build();
   }

   public static Menu getMenu() {
      return Menu.builder()
          .menuType(Menu.MenuType.MENU_TYPE1)
          .build();
   }

   public static MenuPosition getMenuPosition() {
      return MenuPosition.builder()
          .price(BigDecimal.valueOf(1))
          .name("test")
          .build();
   }

   public static Order getOrder() {
      return Order.builder()
          .status(Order.OrderStatus.IN_PROGRESS)
          .timeOfOrder(OffsetDateTime.now())
          .build();
   }

   public static Address getAddress() {
      return Address.builder()
          .ipAddress("ip")
          .city("city")
          .latitude(1D)
          .longitude(1D)
          .postalCode("code")
          .build();
   }

   public static OrderPosition getOrderPosition() {
      return OrderPosition.builder().build();
   }

   public static Restaurant getRestLonLat2() {
      return getRestaurant().withAddress(getAddress()
          .withLatitude(2D).withLongitude(2D)).withDeliveryRange(1000D);
   }

   public static Restaurant getRestLonLat3() {
      return getRestaurant().withAddress(getAddress()
          .withLatitude(3D).withLongitude(3D)).withDeliveryRange(1000D);
   }

   public static Restaurant getRestLonLat1() {
      return getRestaurant().withAddress(getAddressLonLat1()).withDeliveryRange(0D);
   }

   public static Address getAddressLonLat1() {
      return getAddress().withLatitude(1D).withLongitude(1D);
   }

   public static Account getAccount() {
      HashSet hashSet = new HashSet<>();
      hashSet.add(getAccountRole());
      return Account.builder().active(true)
          .roles(hashSet)
          .userName("someTest")
          .password("someTest")
          .build();
   }

   public static Role getAccountRole() {
      return Role.builder().roleName(Role.ACCOUNT_ROLE.ACCOUNT).build();
   }

   public static Role getSellerRole() {
      return Role.builder().roleName(Role.ACCOUNT_ROLE.SELLER).build();
   }

   public static ImageEntity getImage() {
      return ImageEntity.builder()
          .image(new byte[]{1,2,3})
          .build();
   }
}