package code.component.manageRestaurant.data.jpa;

import code.component.manageRestaurant.domain.MenuEntity;
import code.component.manageRestaurant.domain.MenuPositionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuPositionJpaRepo extends JpaRepository<MenuPositionEntity, Integer> {

   @EntityGraph(
       type = EntityGraph.EntityGraphType.FETCH,
       attributePaths = {
           "images"
       })
   Page<MenuPositionEntity> getPageByMenu(MenuEntity menu, Pageable pageable);


   @Override
   @EntityGraph(
       type = EntityGraph.EntityGraphType.FETCH,
       attributePaths = {
           "images"
       })
   List<MenuPositionEntity> findAll(Sort sort);
}