package code.component.manageRestaurant.manageImages;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class ImageController {

   public static final String IMAGE_FILE = "/images/get/{imageId}";
   private ImageDAO imageDAO;

   @GetMapping(value = IMAGE_FILE, produces = MediaType.IMAGE_JPEG_VALUE)
   public ResponseEntity<Resource> getImage(
       @PathVariable("imageId") Integer imageId
   ) {
      ImageEntity image = imageDAO.getImageById(imageId);
      ByteArrayResource file = new ByteArrayResource(image.getImage());
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION,
              "attachment; filename=\"" + file.getFilename() + "\"")
          .body(file);
   }

}