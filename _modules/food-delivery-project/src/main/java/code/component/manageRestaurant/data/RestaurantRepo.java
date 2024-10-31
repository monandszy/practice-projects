package code.component.manageRestaurant.data;

import code.component.manageAccount.data.AccountJpaRepo;
import code.component.manageAccount.domain.AccountEntity;
import code.component.manageAccount.domain.mapper.AccountEntityMapper;
import code.component.manageRestaurant.dao.RestaurantDAO;
import code.component.manageRestaurant.data.jpa.RestaurantJpaRepo;
import code.component.manageRestaurant.domain.Restaurant;
import code.component.manageRestaurant.domain.RestaurantEntity;
import code.component.manageRestaurant.domain.mapper.RestaurantEntityMapper;
import code.component.manageRestaurant.manageDelivery.AddressJpaRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

import static code.configuration.Constants.PAGE_SIZE;

@Repository
@AllArgsConstructor
public class RestaurantRepo implements RestaurantDAO {

   private RestaurantJpaRepo restaurantJpaRepo;
   private AccountJpaRepo accountJpaRepo;
   private AddressJpaRepo addressJpaRepo;
   private RestaurantEntityMapper entityMapper;
   private AccountEntityMapper accountEntityMapper;

   @Override
   public Restaurant add(Restaurant restaurant, Integer addressId, String sellerId) {
      RestaurantEntity save = entityMapper.mapToEntity(restaurant);
      save.setSeller(accountJpaRepo.findByUserName(sellerId).orElseThrow());
      save.setAddress(addressJpaRepo.findById(addressId).orElseThrow());
      return entityMapper.mapFromEntity(restaurantJpaRepo.save(save));
   }

   @Override
   public Restaurant getByRestaurantId(Integer restaurantId) {
      RestaurantEntity restaurant = restaurantJpaRepo.findById(restaurantId).orElseThrow();
      return entityMapper.mapFromEntity(restaurant).withSeller(
          accountEntityMapper.mapFromEntity(restaurant.getSeller()));
   }

   @Override
   public List<Restaurant> getPageBySeller(String sellerId, Integer page) {
      AccountEntity seller = accountJpaRepo.findByUserName(sellerId).orElseThrow();
      Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("id"));
      Page<RestaurantEntity> pageBySeller = restaurantJpaRepo.getPageBySeller(seller, pageable);
      return pageBySeller.getContent().stream().map(entityMapper::mapFromEntity).toList();
   }

   @Override
   public void deleteById(Integer id) {
      restaurantJpaRepo.deleteById(id);
   }

   @Override
   public void updateRange(Integer restaurantId, Double range) {
      RestaurantEntity byId = restaurantJpaRepo.findById(restaurantId).orElseThrow();
      byId.setDeliveryRange(range);
      restaurantJpaRepo.save(byId);
   }


   @Override
   public List<Restaurant> getAllWithAddress() {
      return entityMapper.mapRFromEntityList(restaurantJpaRepo.findAll());
   }

}