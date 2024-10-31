package code.component.manageRestaurant.manageDelivery;

import code.component.api.ipAddressApi.ApiClientImpl;
import code.component.manageRestaurant.dao.RestaurantDAO;
import code.component.manageRestaurant.domain.Restaurant;
import code.component.manageRestaurant.manageDelivery.domain.Address;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddressService {

   private AddressDAO addressDAO;
   private RestaurantDAO restaurantDAO;
   private ApiClientImpl apiClient;
   private DistanceCalculationService distanceCalculationService;

   public List<Restaurant> getPageByAddress(Address clientAddress, Integer pageNumber) {
      List<Restaurant> restaurants = restaurantDAO.getAllWithAddress();
      Double longitude = clientAddress.getLongitude();
      Double latitude = clientAddress.getLatitude();
      Map<Double, Restaurant> lowestDistance = new HashMap<>();
      for (Restaurant restaurant : restaurants) {
         Double distance = distanceCalculationService.calculateDistance(
             longitude, latitude,
             restaurant.getAddress().getLongitude(),
             restaurant.getAddress().getLatitude());
         if ((restaurant.getDeliveryRange() - distance > 0)) {
            lowestDistance.put(distance, restaurant);
         }
      }
      return lowestDistance.entrySet().stream()
          .sorted(Comparator.comparing(e -> e.getKey()))
          .map(e -> e.getValue())
          .skip(pageNumber*10).limit(10).collect(Collectors.toList());
   }

   public Address getAddress(String ip) {
      Optional<Address> address = addressDAO.getByIp(ip);
      if (address.isPresent()) {
         return address.get();
      } else {
         Address addressFromApi = apiClient.getAddressFromApi(ip);
         addAddress(addressFromApi);
         return addressFromApi;
      }
   }

   public void addAddress(Address address) {
      addressDAO.add(address);
   }
}