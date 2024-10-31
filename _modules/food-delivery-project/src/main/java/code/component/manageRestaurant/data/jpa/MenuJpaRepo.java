package code.component.manageRestaurant.data.jpa;

import code.component.manageRestaurant.domain.MenuEntity;
import code.component.manageRestaurant.domain.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuJpaRepo extends JpaRepository<MenuEntity, Integer> {
   Page<MenuEntity> getPageByRestaurant(RestaurantEntity restaurant, Pageable pageable);
}