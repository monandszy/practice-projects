package code.configuration;

import code.component.manageAccount.AccountService;
import code.component.manageAccount.LoginService;
import code.component.manageAccount.domain.Account;
import code.component.manageAccount.domain.Role;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component 
@AllArgsConstructor
public class BootstrapApplicationComponent implements ApplicationListener<ContextRefreshedEvent> {

   private LoginService loginService;
   private AccountService accountService;

   @Override
   public void onApplicationEvent(final @NonNull ContextRefreshedEvent event) {
      try {
         String userName = "admin";
         Account admin = Account.builder()
             .userName(userName)
             .password(userName)
             .build();
         loginService.register(admin);
         accountService.setRole(userName, Role.builder().roleName(Role.ACCOUNT_ROLE.SELLER).build());
         accountService.setRole(userName, Role.builder().roleName(Role.ACCOUNT_ROLE.ADMIN).build());
         accountService.setRole(userName, Role.builder().roleName(Role.ACCOUNT_ROLE.CLIENT).build());
      } catch (Exception e) {}
      try {
         String userName = "anonymousUser";
         Account anonymousUser = Account.builder()
             .userName(userName)
             .password(userName)
             .build();
         loginService.register(anonymousUser);
         accountService.setRole(userName, Role.builder().roleName(Role.ACCOUNT_ROLE.SELLER).build());
         accountService.setRole(userName, Role.builder().roleName(Role.ACCOUNT_ROLE.ADMIN).build());
         accountService.setRole(userName, Role.builder().roleName(Role.ACCOUNT_ROLE.CLIENT).build());
      } catch (Exception e) {}
   }
}