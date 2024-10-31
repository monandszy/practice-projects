package code.component.manageAccount.web;

import code.component.manageAccount.AccountService;
import code.component.manageAccount.domain.Account;
import code.component.manageAccount.domain.RoleDTO;
import code.component.manageAccount.domain.mapper.AccountDTOMapper;
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
public class HomeController {

   public static final String ACCOUNTS = "home";
   public static final String ACCOUNTS_DELETE = "home/delete/{userName}";
   public static final String ACCOUNTS_SET_ROLE = "home/set/{userName}";

   private final AccountService accountService;
   private final AccountDTOMapper accountDTOMapper;

   @GetMapping(value = ACCOUNTS)
   public String getAccounts(
       @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
       Model model
   ) {
      pageNumber = Objects.isNull(pageNumber) ? Integer.valueOf(START_PAGE) : pageNumber;
      model.addAttribute("pageNumber", pageNumber);
      List<Account> accountPage = accountService.getAccountPage(pageNumber);
      model.addAttribute("accountPage", accountDTOMapper.mapAToDTOList(accountPage));
      return "home";
   }

   @PostMapping(value = ACCOUNTS_SET_ROLE)
   public String setRole(
       @PathVariable("userName") String userName,
       @ModelAttribute("role") @Valid RoleDTO role
   ) {
      accountService.setRole(userName, accountDTOMapper.mapFromDTO(role));
      return "redirect:/home";
   }

   @PostMapping(value = ACCOUNTS_DELETE)
   public String deleteAccount(
       @PathVariable("userName") String userName
   ) {
      accountService.deleteAccount(userName);
      return "redirect:/home";
   }
}