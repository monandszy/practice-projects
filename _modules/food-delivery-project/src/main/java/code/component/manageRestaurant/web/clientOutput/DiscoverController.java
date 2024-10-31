package code.component.manageRestaurant.web.clientOutput;

import code.component.manageAccount.AccountService;
import code.component.manageRestaurant.domain.RestaurantDTO;
import code.component.manageRestaurant.domain.mapper.RestaurantDTOMapper;
import code.component.manageRestaurant.manageDelivery.AddressService;
import code.component.manageRestaurant.manageDelivery.domain.Address;
import code.component.manageRestaurant.manageDelivery.domain.IpDTO;
import code.configuration.Constants;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

import static code.configuration.Constants.START_PAGE;

@Controller
@AllArgsConstructor
public class DiscoverController {

   public static final String DISCOVER = "discover";
   public static final String DISCOVER_BY_IP = "discover/ip";
   private AddressService addressService;
   private RestaurantDTOMapper dtoMapper;
   private AccountService accountService;

   @GetMapping(value = DISCOVER_BY_IP)
   public String getRestaurantsByIp(
       @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
       @ModelAttribute(value = "ip") @Valid IpDTO ipDto,
       Model model,
       HttpSession session
   ) {
      String ip = ipDto.getIp();
      model.addAttribute(Constants.USERNAME, accountService.getAuthenticatedUserName());
      pageNumber = Objects.isNull(pageNumber) ? Integer.valueOf(START_PAGE) : pageNumber;
      model.addAttribute("pageNumber", pageNumber);
      Address address = (Address) session.getAttribute(Constants.ADDRESS);
      if (Objects.isNull(address) || Objects.nonNull(ip)) {
         address = addressService.getAddress(ip);
         session.setAttribute(Constants.ADDRESS, address);
      }
      model.addAttribute("ipDTO", ipDto);
      List<RestaurantDTO> restaurantPage = dtoMapper.mapRToDTOList(
          addressService.getPageByAddress(address, pageNumber));
      model.addAttribute("restaurantsByAddressPage", restaurantPage);

      return "client/discover";
   }

   @GetMapping(value = DISCOVER)
   public String getRestaurants(
       @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
       Model model,
       HttpSession session
   ) {
      model.addAttribute(Constants.USERNAME, accountService.getAuthenticatedUserName());
      pageNumber = Objects.isNull(pageNumber) ? Integer.valueOf(START_PAGE) : pageNumber;
      model.addAttribute("pageNumber", pageNumber);

      String ip = accountService.getCurrentIp();
      Address address = addressService.getAddress(ip);
      model.addAttribute("ipDTO", new IpDTO().withIp(ip));
      session.setAttribute(Constants.ADDRESS, address);
      List<RestaurantDTO> restaurantPage = dtoMapper.mapRToDTOList(
          addressService.getPageByAddress(address, pageNumber));
      model.addAttribute("restaurantsByAddressPage", restaurantPage);

      return "client/discover";
   }

}