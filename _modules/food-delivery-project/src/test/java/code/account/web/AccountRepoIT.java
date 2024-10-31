package code.account.web;

import code.component.manageAccount.data.AccountJpaRepo;
import code.component.manageAccount.data.AccountRepo;
import code.component.manageAccount.domain.Account;
import code.component.manageAccount.domain.Role;
import code.component.manageAccount.domain.mapper.AccountEntityMapperImpl;
import code.configuration.AbstractJpaIT;
import code.util.DataFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static code.component.manageAccount.domain.Role.ACCOUNT_ROLE.ACCOUNT;

@Import(value = {
    AccountRepo.class,
    AccountEntityMapperImpl.class
})
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AccountRepoIT extends AbstractJpaIT {

   private AccountRepo accountRepo;
   private AccountJpaRepo accountJpaRepo;

   @Test
   void testAccountCrud() {
      Account account = DataFixtures.getAccount();
      accountRepo.addAccount(account, Role.builder().roleName(ACCOUNT).build());
      String userName = account.getUserName();
      List<Account> accountPage = accountRepo.getAccountPage(0);
      Assertions.assertFalse(accountPage.isEmpty());

      accountRepo.setRole(userName, DataFixtures.getSellerRole());
      Optional<Account> byUserName = accountRepo.findByUserName(userName);
      Assertions.assertTrue(byUserName.isPresent());
      Assertions.assertFalse(byUserName.get().getRoles().contains(DataFixtures.getSellerRole()));

      accountRepo.deleteByUserName(userName);
      List<Account> accountPage2 = accountRepo.getAccountPage(0);
      Assertions.assertTrue(accountPage.size() > accountPage2.size());
   }

}