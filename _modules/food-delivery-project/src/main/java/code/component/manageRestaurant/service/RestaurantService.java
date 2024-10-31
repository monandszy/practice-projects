package code.component.manageRestaurant.service;

import code.component.manageRestaurant.dao.RestaurantDAO;
import code.component.manageRestaurant.domain.Restaurant;
import code.component.manageRestaurant.manageDelivery.AddressDAO;
import code.component.manageRestaurant.manageDelivery.domain.Address;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {
   private RestaurantDAO restaurantDAO;
   private AddressDAO addressDAO;

   @Transactional
   public Integer add(Restaurant restaurant, Address address, String sellerId) {
      Address add = addressDAO.addOrFindByIp(address);
      return restaurantDAO.add(restaurant, add.getId(), sellerId).getId();
   }

   @Transactional
   public List<Restaurant> getPageBySellerId(String sellerId, Integer page) {
      return restaurantDAO.getPageBySeller(sellerId, page);
   }

   @Transactional
   public void deleteById(Integer id) {
      restaurantDAO.deleteById(id);
   }

   @Transactional
   public void update(Integer restaurantId, Double deliveryRange) {
      restaurantDAO.updateRange(restaurantId, deliveryRange);
   }
}