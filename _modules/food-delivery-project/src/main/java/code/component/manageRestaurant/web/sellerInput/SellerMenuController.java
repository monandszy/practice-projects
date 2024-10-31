package code.component.manageRestaurant.web.sellerInput;

import code.component.manageRestaurant.domain.MenuPositionDTO;
import code.component.manageRestaurant.domain.mapper.RestaurantDTOMapper;
import code.component.manageRestaurant.service.MenuPositionService;
import code.configuration.Constants;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

import static code.configuration.Constants.START_PAGE;

@Controller
@AllArgsConstructor
public class SellerMenuController {

   public static final String MY_MENU = "myMenu";
   public static final String MY_MENU_GET = MY_MENU + "/get/{menuId}";
   public static final String MY_MENU_ADD = MY_MENU + "/add";
   public static final String MY_MENU_DELETE = MY_MENU + "/delete/{menuPositionId}";

   private MenuPositionService menuPositionService;
   private RestaurantDTOMapper dtoMapper;

   @GetMapping(value = MY_MENU_GET)
   public String getMenuViewById(
       @PathVariable(value = "menuId") String menuId,
       @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
       HttpSession session,
       Model model
   ) {
      Integer restaurantId = (Integer) session.getAttribute(Constants.RESTAURANT);
      model.addAttribute("menuPositionDTO", MenuPositionDTO.builder().build());
      model.addAttribute("menuId", menuId);
      model.addAttribute("restaurantId", restaurantId);
      pageNumber = Objects.isNull(pageNumber) ? START_PAGE : pageNumber;
      model.addAttribute("pageNumber", pageNumber);
      List<MenuPositionDTO> menuPage = dtoMapper.mapMPToDTOList(
          menuPositionService.getPageByMenu(Integer.valueOf(menuId), pageNumber));

      model.addAttribute("menuPage", menuPage);
      return "seller/myMenu";
   }

   @PostMapping(value = MY_MENU_ADD)
   public String postMenuPosition(
       @ModelAttribute("menuPositionDTO") @Valid MenuPositionDTO menuPositionDTO,
       @RequestPart(value = "image", required = false) MultipartFile image,
       @RequestParam("menuId") Integer menuId
   ) {
      menuPositionService.add(image, dtoMapper.mapFromDTO(menuPositionDTO), menuId);
      return "redirect:/myMenu/get/%s" .formatted(menuId);
   }

   @PostMapping(value = MY_MENU_DELETE)
   public String deleteMenuPosition(
       @PathVariable("menuPositionId") Integer menuPositionId,
       @ModelAttribute("menuId") Integer menuId
   ) {
      menuPositionService.deleteById(menuPositionId);
      return "redirect:/myMenu/get/%s" .formatted(menuId);
   }
}