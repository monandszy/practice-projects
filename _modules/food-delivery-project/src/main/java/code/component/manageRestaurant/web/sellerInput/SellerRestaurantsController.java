package code.component.manageRestaurant.web.sellerInput;

import code.component.manageAccount.AccountService;
import code.component.manageRestaurant.domain.RestaurantDTO;
import code.component.manageRestaurant.domain.mapper.RestaurantDTOMapper;
import code.component.manageRestaurant.manageDelivery.AddressService;
import code.component.manageRestaurant.manageDelivery.domain.Address;
import code.component.manageRestaurant.service.RestaurantService;
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
public class SellerRestaurantsController {

   public static final String MY_RESTAURANTS = "myRestaurants";
   public static final String MY_RESTAURANTS_GET = MY_RESTAURANTS + "/get";
   public static final String MY_RESTAURANTS_ADD = MY_RESTAURANTS + "/add";
   public static final String MY_RESTAURANTS_DELETE = MY_RESTAURANTS + "/delete/{restaurantId}";

   private RestaurantService restaurantService;
   private AccountService accountService;
   private AddressService addressService;
   private RestaurantDTOMapper dtoMapper;

   @GetMapping(value = MY_RESTAURANTS_GET)
   public String getRestaurantsViewBySellerId(
       @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
       Model model
   ) {
      model.addAttribute("restaurantDTO", new RestaurantDTO());
      pageNumber = Objects.isNull(pageNumber) ? Integer.valueOf(START_PAGE) : pageNumber;
      model.addAttribute("pageNumber", pageNumber);
      String sellerId = accountService.getAuthenticatedUserName();
      List<RestaurantDTO> restaurantsPage = dtoMapper.mapRToDTOList(restaurantService.getPageBySellerId(sellerId, pageNumber));
      model.addAttribute("restaurantsPage", restaurantsPage);
      return "seller/myRestaurants";
   }

   @PostMapping(value = MY_RESTAURANTS_ADD)
   public String postRestaurant(
       @ModelAttribute("restaurantDTO") @Valid RestaurantDTO restaurantDTO
   ) {
      Address address = addressService.getAddress(accountService.getCurrentIp());
      String sellerId = accountService.getAuthenticatedUserName();
      restaurantService.add(dtoMapper.mapFromDTO(restaurantDTO)
          , address, sellerId);
      return "redirect:/myRestaurants/get";
   }

   @PostMapping(value = MY_RESTAURANTS_DELETE)
   public String deleteRestaurant(
       @PathVariable("restaurantId") Integer restaurantId
   ) {
      restaurantService.deleteById(restaurantId);
      return "redirect:/myRestaurants/get";
   }
}