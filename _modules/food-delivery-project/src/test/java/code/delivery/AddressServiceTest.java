package code.delivery;

import code.component.api.ipAddressApi.ApiClientImpl;
import code.component.manageAccount.AccountService;
import code.component.manageRestaurant.dao.RestaurantDAO;
import code.component.manageRestaurant.domain.Restaurant;
import code.component.manageRestaurant.manageDelivery.AddressDAO;
import code.component.manageRestaurant.manageDelivery.AddressService;
import code.component.manageRestaurant.manageDelivery.DistanceCalculationService;
import code.util.DataFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

   @InjectMocks
   private AddressService addressService;
   @Spy
   private DistanceCalculationService distanceCalculationService =
       new DistanceCalculationService();

   @Mock
   private AddressDAO addressDAO;
   @Mock
   private RestaurantDAO restaurantDAO;
   @Mock
   private AccountService accountService;
   @Mock
   private ApiClientImpl apiClient;


   @Test
   void testDistanceCalulation() {
      double lat1 = 40.714268; // New York
      double lon1 = -74.005974;
      double lat2 = 34.0522; // Los Angeles
      double lon2 = -118.2437;

      double distance = distanceCalculationService
          .calculateDistance(lon1, lat1, lon2, lat2);

      double expectedDistance = 3944;
      Assertions.assertTrue(Math.abs(distance - expectedDistance) < 10);
   }

   @Test
   void testAddressPriority() {
      Restaurant restLonLat2 = DataFixtures.getRestLonLat2();
      Restaurant restLonLat3 = DataFixtures.getRestLonLat3();
      List<Restaurant> restaurants = List.of(DataFixtures.getRestLonLat1(),
          restLonLat2, restLonLat3);
      Mockito.when(restaurantDAO.getAllWithAddress()).thenReturn(restaurants);
      List<Restaurant> pageByAddress = addressService.getPageByAddress(DataFixtures.getAddressLonLat1(), 0);
      Assertions.assertTrue(pageByAddress.size() == 2);
      Assertions.assertTrue(pageByAddress.get(0) == restLonLat2);
      Assertions.assertTrue(pageByAddress.get(1) == restLonLat3);
   }
}