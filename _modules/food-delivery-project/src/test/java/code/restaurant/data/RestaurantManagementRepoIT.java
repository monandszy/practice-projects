package code.restaurant.data;

import code.component.manageAccount.data.AccountRepo;
import code.component.manageAccount.domain.Account;
import code.component.manageAccount.domain.mapper.AccountEntityMapperImpl;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Import(value = {
    RestaurantRepo.class,
    AddressRepo.class,
    MenuRepo.class,
    MenuPositionRepo.class,
    RestaurantEntityMapperImpl.class,
    AccountEntityMapperImpl.class,
    AddressEntityMapperImpl.class,
    AccountRepo.class
})
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RestaurantManagementRepoIT extends AbstractJpaIT {

   private RestaurantRepo restaurantRepo;
   private AddressRepo addressRepo;
   private AccountRepo accountRepo;
   private MenuRepo menuRepo;
   private MenuPositionRepo menuPositionRepo;


   @Test
   @Transactional
   void testCrud() {
      Address address = addressRepo.addOrFindByIp(DataFixtures.getAddress());
      Account account = DataFixtures.getAccount();
      accountRepo.addAccount(account, DataFixtures.getAccountRole());
      String sellerId = account.getUserName();

      // addRestaurant and getPageBySeller
      restaurantRepo.add(DataFixtures.getRestaurant(), address.getId(), sellerId);
      List<Restaurant> pageBySeller = restaurantRepo.getPageBySeller(sellerId, 0);
      Assertions.assertFalse(pageBySeller.isEmpty());
      Restaurant restaurant = pageBySeller.getFirst();
      Integer restaurantId = restaurant.getId();

      // updateRestaurant and getByRestaurantId
      Double newRange = 2D;
      restaurantRepo.updateRange(restaurantId, newRange);
      Restaurant byId = restaurantRepo.getByRestaurantId(restaurantId);
      Assertions.assertEquals(restaurant, byId);
      Assertions.assertNotNull(byId.getSeller());

      // getAllWithAddress
      List<Restaurant> restaurants = restaurantRepo.getAllWithAddress();
      Assertions.assertNotNull(restaurants.getFirst().getAddress());

      // addMenu and getPageByRestaurantId
      menuRepo.add(DataFixtures.getMenu(), restaurantId);
      List<Menu> pageByRestaurantId = menuRepo.getPageByRestaurantId(restaurantId, 0);
      Assertions.assertFalse(pageByRestaurantId.isEmpty());
      Integer menuId = pageByRestaurantId.getFirst().getId();

      // addPosition and getMenuPositions, getPageByMenuId
      menuPositionRepo.add(DataFixtures.getMenuPosition(), menuId);
      List<MenuPosition> menuPositions = menuPositionRepo.getMenuPositions(menuId);
      List<MenuPosition> pageByMenuId = menuPositionRepo.getPageByMenuId(menuId, 0);
      Assertions.assertFalse(menuPositions.isEmpty());
      Assertions.assertFalse(pageByMenuId.isEmpty());
      Integer menuPositionId = pageByMenuId.getFirst().getId();

      // delete
      menuPositionRepo.deleteById(menuPositionId);
      menuPositions = menuPositionRepo.getMenuPositions(menuId);
      pageByMenuId = menuPositionRepo.getPageByMenuId(menuId, 0);
      Assertions.assertTrue(menuPositions.isEmpty());
      Assertions.assertTrue(pageByMenuId.isEmpty());
      menuRepo.deleteById(menuId);
      pageByRestaurantId = menuRepo.getPageByRestaurantId(restaurantId, 0);
      Assertions.assertTrue(pageByRestaurantId.isEmpty());
      restaurantRepo.deleteById(restaurantId);
      pageBySeller = restaurantRepo.getPageBySeller(sellerId, 0);
      Assertions.assertTrue(pageBySeller.isEmpty());
   }
}