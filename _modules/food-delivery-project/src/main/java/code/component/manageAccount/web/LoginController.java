package code.component.manageAccount.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class LoginController {

   public static final String LOGIN = "/login";

   @GetMapping(LOGIN)
   public String getLoginView() {
      return "login";
   }
}