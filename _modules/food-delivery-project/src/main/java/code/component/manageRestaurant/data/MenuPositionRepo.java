package code.component.manageRestaurant.data;

import code.component.manageRestaurant.dao.MenuPositionDAO;
import code.component.manageRestaurant.data.jpa.MenuJpaRepo;
import code.component.manageRestaurant.data.jpa.MenuPositionJpaRepo;
import code.component.manageRestaurant.domain.MenuEntity;
import code.component.manageRestaurant.domain.MenuPosition;
import code.component.manageRestaurant.domain.MenuPositionEntity;
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
public class MenuPositionRepo implements MenuPositionDAO {

   private MenuPositionJpaRepo menuPositionJpaRepo;
   private MenuJpaRepo menuJpaRepo;
   private RestaurantEntityMapper entityMapper;

   @Override
   public MenuPosition add(MenuPosition menuPosition, Integer menuId) {
      MenuPositionEntity save = entityMapper.mapToEntity(menuPosition);
      save.setMenu(menuJpaRepo.findById(menuId).orElseThrow(
          () -> new EntityNotFoundException("menu not found")));
      return entityMapper.mapFromEntity(menuPositionJpaRepo.save(save));
   }

   @Override
   public List<MenuPosition> getPageByMenuId(Integer menuId, Integer page) {
      MenuEntity menu = menuJpaRepo.findById(menuId).orElseThrow();
      Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("id"));
      Page<MenuPositionEntity> pageBySeller = menuPositionJpaRepo.getPageByMenu(menu, pageable);
      return pageBySeller.getContent().stream().map(entityMapper::mapFromEntity).toList();
   }

   @Override
   public void deleteById(Integer id) {
      menuPositionJpaRepo.deleteById(id);
   }

   @Override
   public List<MenuPosition> getMenuPositions(Integer menuId) {
      return menuPositionJpaRepo.findAll(Sort.by("id")).stream()
          .map(entityMapper::mapFromEntity).toList();
   }
}