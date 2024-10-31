package code.component.manageRestaurant.manageImages;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageJpaRepo extends JpaRepository<ImageEntity, Integer> {
}