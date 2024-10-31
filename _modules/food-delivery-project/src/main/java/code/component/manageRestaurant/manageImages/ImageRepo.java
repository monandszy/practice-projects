package code.component.manageRestaurant.manageImages;

import code.component.manageRestaurant.data.jpa.MenuPositionJpaRepo;
import code.component.manageRestaurant.domain.MenuPositionEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ImageRepo implements ImageDAO {

   private ImageJpaRepo imageJpaRepo;
   private MenuPositionJpaRepo menuPositionJpaRepo;

   @Override
   public void add(ImageEntity image, Integer menuPositionId) {
      MenuPositionEntity byId = menuPositionJpaRepo.findById(menuPositionId).orElseThrow();
      image.setMenuPosition(byId);
      imageJpaRepo.save(image);
   }

   @Override
   public ImageEntity getImageById(int i) {
      return imageJpaRepo.findById(i).orElseThrow();
   }
}