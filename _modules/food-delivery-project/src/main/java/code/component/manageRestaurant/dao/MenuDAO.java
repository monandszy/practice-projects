package code.component.manageRestaurant.dao;

import code.component.manageRestaurant.domain.Menu;

import java.util.List;

public interface MenuDAO extends CrudDAO<Menu> {

   Menu add(Menu menu, Integer restaurantId);

   List<Menu> getPageByRestaurantId(Integer restaurantId, Integer page);
}