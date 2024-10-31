package code.component.manageRestaurant.service;

import code.component.manageRestaurant.dao.MenuPositionDAO;
import code.component.manageRestaurant.domain.MenuPosition;
import code.component.manageRestaurant.manageImages.ImageDAO;
import code.component.manageRestaurant.manageImages.ImageEntity;
import code.web.exception.DeliveryError;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class MenuPositionService {
   private MenuPositionDAO menuPositionDAO;
   private ImageDAO imageDAO;

   @Transactional
   public Integer add(MultipartFile image, MenuPosition menuPosition, Integer menuId) {
      MenuPosition created = menuPositionDAO.add(menuPosition, menuId);
      if (Objects.nonNull(image)) {
         try {
            ImageEntity build = ImageEntity.builder().image(image.getBytes()).build();
            imageDAO.add(build, created.getId());
         } catch (IOException e) {
            throw new DeliveryError("Invalid Image [%s]".formatted(e.getMessage()));
         }
      }
      return created.getId();
   }

   @Transactional
   public List<MenuPosition> getPageByMenu(Integer menuId, Integer page) {
      return menuPositionDAO.getPageByMenuId(menuId, page);
   }

   @Transactional
   public void deleteById(Integer id) {
      menuPositionDAO.deleteById(id);
   }

   @Transactional
   public List<MenuPosition> getAllMenuPositions(Integer menuId) {
      return menuPositionDAO.getMenuPositions(menuId);
   }
}