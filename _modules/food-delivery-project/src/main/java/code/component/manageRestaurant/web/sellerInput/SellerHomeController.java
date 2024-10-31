package code.component.manageRestaurant.web.sellerInput;

import code.component.manageAccount.AccountService;
import code.configuration.Constants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class SellerHomeController {

   public static final String MANAGE = "manage";
   private AccountService accountService;

   @GetMapping(MANAGE)
   public String redirect(Model model) {
      model.addAttribute(Constants.USERNAME, accountService.getAuthenticatedUserName());
      return "seller/manage";
   }
}