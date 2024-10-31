package code.component.manageRestaurant.dao;

import code.component.manageRestaurant.domain.MenuPosition;

import java.util.List;

public interface MenuPositionDAO extends CrudDAO<MenuPosition> {

   MenuPosition add(MenuPosition menuPosition, Integer relationId);

   List<MenuPosition> getPageByMenuId(Integer menuId, Integer page);

   List<MenuPosition> getMenuPositions(Integer menuId);
}