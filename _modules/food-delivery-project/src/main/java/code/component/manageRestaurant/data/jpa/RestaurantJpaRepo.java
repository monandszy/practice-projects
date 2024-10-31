package code.component.manageRestaurant.data.jpa;

import code.component.manageAccount.domain.AccountEntity;
import code.component.manageRestaurant.domain.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantJpaRepo extends JpaRepository<RestaurantEntity, Integer> {
   Page<RestaurantEntity> getPageBySeller(AccountEntity seller, Pageable pageable);

   @NonNull
   @EntityGraph(
       type = EntityGraph.EntityGraphType.FETCH,
       attributePaths = "seller"
   )
   @Override
   Optional<RestaurantEntity> findById(@NonNull Integer restaurantId);


   @Override
   List<RestaurantEntity> findAll();
}