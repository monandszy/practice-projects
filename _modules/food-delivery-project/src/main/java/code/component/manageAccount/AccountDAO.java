package code.component.manageAccount;

import code.component.manageAccount.domain.Account;
import code.component.manageAccount.domain.Role;

import java.util.List;
import java.util.Optional;

public interface AccountDAO {
   Optional<Account> findByUserName(String username);

   void addAccount(Account account, Role role);

   List<Account> getAccountPage(Integer pageNumber);

   void deleteByUserName(String accountId);

   void setRole(String userName, Role role);
}