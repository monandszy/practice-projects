package code.component.manageRestaurant.web.sellerInput;

import code.component.manageRestaurant.domain.MenuDTO;
import code.component.manageRestaurant.domain.mapper.RestaurantDTOMapper;
import code.component.manageRestaurant.service.MenuService;
import code.component.manageRestaurant.service.RestaurantService;
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

import java.util.List;
import java.util.Objects;

import static code.configuration.Constants.START_PAGE;

@Controller
@AllArgsConstructor
public class SellerRestaurantController {

   public static final String MY_RESTAURANT = "myRestaurant";
   public static final String MY_RESTAURANT_GET = MY_RESTAURANT + "/get/{restaurantId}";
   public static final String MY_RESTAURANT_ADD = MY_RESTAURANT + "/add";
   public static final String MY_RESTAURANT_DELETE = MY_RESTAURANT + "/delete/{menuId}";
   public static final String MY_RESTAURANT_UPDATE = MY_RESTAURANT + "/update/{restaurantId}";

   private MenuService menuService;
   private RestaurantService restaurantService;
   private RestaurantDTOMapper dtoMapper;

   @GetMapping(value = MY_RESTAURANT_GET)
   public String getRestaurantViewById(
       @PathVariable(value = "restaurantId") Integer restaurantId,
       @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
       HttpSession session,
       Model model
   ) {
      session.setAttribute(Constants.RESTAURANT, restaurantId);
      model.addAttribute("menuDTO", new MenuDTO());
      model.addAttribute("restaurantId", restaurantId);
      pageNumber = Objects.isNull(pageNumber) ? START_PAGE : pageNumber;
      model.addAttribute("pageNumber", pageNumber);
      List<MenuDTO> restaurantPage = dtoMapper.mapMToDTOList(
          menuService.getPageByRestaurant(restaurantId, pageNumber));
      model.addAttribute("restaurantPage", restaurantPage);
      return "seller/myRestaurant";
   }

   @PostMapping(value = MY_RESTAURANT_ADD)
   public String postMenu(
       @ModelAttribute("menuDTO") @Valid MenuDTO menuDTO,
       HttpSession session
   ) {
      Integer restaurantId = (Integer) session.getAttribute(Constants.RESTAURANT);
      menuService.add(dtoMapper.mapFromDTO(menuDTO), restaurantId);
      return "redirect:/myRestaurant/get/%s".formatted(restaurantId);
   }

   @PostMapping(value = MY_RESTAURANT_DELETE)
   public String deleteMenu(
       @PathVariable("menuId") Integer menuId,
       HttpSession session
   ) {
      Integer restaurantId = (Integer) session.getAttribute(Constants.RESTAURANT);
      menuService.deleteById(menuId);
      return "redirect:/myRestaurant/get/%s".formatted(restaurantId);
   }

   @PostMapping(MY_RESTAURANT_UPDATE)
   public String updateRestaurant(
       @PathVariable("restaurantId") Integer restaurantId,
       @RequestParam("deliveryRange") Double deliveryRange
   ) {
      restaurantService.update(restaurantId, deliveryRange);
      return "redirect:/myRestaurant/get/%s".formatted(restaurantId);
   }

}