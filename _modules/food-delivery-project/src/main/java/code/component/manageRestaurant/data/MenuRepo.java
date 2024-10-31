package code.component.manageRestaurant.data;

import code.component.manageRestaurant.dao.MenuDAO;
import code.component.manageRestaurant.data.jpa.MenuJpaRepo;
import code.component.manageRestaurant.data.jpa.RestaurantJpaRepo;
import code.component.manageRestaurant.domain.Menu;
import code.component.manageRestaurant.domain.MenuEntity;
import code.component.manageRestaurant.domain.RestaurantEntity;
import code.component.manageRestaurant.domain.mapper.RestaurantEntityMapper;
import jakarta.persistence.EntityNotFoundException;
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
public class MenuRepo implements MenuDAO {

   private MenuJpaRepo menuJpaRepo;
   private RestaurantJpaRepo restaurantJpaRepo;
   private RestaurantEntityMapper entityMapper;

   @Override
   public Menu add(Menu menu, Integer restaurantId) {
      MenuEntity save = entityMapper.mapToEntity(menu);
      save.setRestaurant(restaurantJpaRepo.findById(restaurantId)
          .orElseThrow(() -> new EntityNotFoundException("restaurant not found")));
      return entityMapper.mapFromEntity(menuJpaRepo.save(save));
   }

   @Override
   public void deleteById(Integer id) {
      menuJpaRepo.deleteById(id);
   }

   @Override
   public List<Menu> getPageByRestaurantId(Integer restaurantId, Integer page) {
      RestaurantEntity restaurant = restaurantJpaRepo.findById(restaurantId).orElseThrow();
      Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("id"));
      Page<MenuEntity> pageBySeller = menuJpaRepo.getPageByRestaurant(restaurant, pageable);
      return pageBySeller.getContent().stream().map(entityMapper::mapFromEntity).toList();
   }
}