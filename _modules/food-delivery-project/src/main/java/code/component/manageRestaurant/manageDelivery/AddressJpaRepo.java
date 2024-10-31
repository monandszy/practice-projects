package code.component.manageRestaurant.manageDelivery;

import code.component.manageRestaurant.manageDelivery.domain.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressJpaRepo extends JpaRepository<AddressEntity, Integer> {

   Optional<AddressEntity> findByIpAddress(String ip);
}