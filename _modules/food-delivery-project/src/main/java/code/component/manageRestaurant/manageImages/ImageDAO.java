package code.component.manageRestaurant.manageImages;

public interface ImageDAO {
   void add(ImageEntity image, Integer menuPositionId);

   ImageEntity getImageById(int i);
}