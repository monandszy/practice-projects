package code.component.manageRestaurant.web.clientOutput;

import code.component.manageRestaurant.domain.MenuDTO;
import code.component.manageRestaurant.domain.mapper.RestaurantDTOMapper;
import code.component.manageRestaurant.service.MenuService;
import code.configuration.Constants;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

import static code.configuration.Constants.START_PAGE;

@Controller
@AllArgsConstructor
public class RestaurantController {

   public static final String RESTAURANT = "restaurant";
   public static final String RESTAURANT_GET = RESTAURANT + "/get/{restaurantId}";

   private MenuService menuService;
   private RestaurantDTOMapper dtoMapper;

   @GetMapping(value = RESTAURANT_GET)
   public String getMenusView(
       @PathVariable(value = "restaurantId") Integer restaurantId,
       @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
       Model model,
       HttpSession session
   ) {
      session.setAttribute(Constants.RESTAURANT, restaurantId);
      model.addAttribute("restaurantId", restaurantId);
      pageNumber = Objects.isNull(pageNumber) ? Integer.valueOf(START_PAGE) : pageNumber;
      model.addAttribute("pageNumber", pageNumber);
      List<MenuDTO> restaurantPage = dtoMapper.mapMToDTOList(
          menuService.getPageByRestaurant(restaurantId, pageNumber));
      model.addAttribute("restaurantPage", restaurantPage);
      return "client/restaurant";
   }
}