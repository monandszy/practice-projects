package code.component.manageAccount;

import code.component.manageAccount.domain.Account;
import code.component.manageAccount.domain.Role;
import code.web.exception.DeliveryError;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static code.component.manageAccount.domain.Role.ACCOUNT_ROLE.ACCOUNT;

@Service
@AllArgsConstructor
public class LoginService implements UserDetailsService {

   private AccountDAO accountDAO;
   private PasswordEncoder passwordEncoder;

   @Override
   @Transactional
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Account account = accountDAO.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException(username));
      List<GrantedAuthority> authorities = getAccountAuthority(account.getRoles());
      return buildAccountForAuthentication(account, authorities);
   }

   private List<GrantedAuthority> getAccountAuthority(Set<Role> roles) {
      return roles.stream()
          .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role.getRoleName().toString()))
          .distinct()
          .toList();
   }

   private UserDetails buildAccountForAuthentication(
       Account account, List<GrantedAuthority> authorities) {
      return new User(
          account.getUserName(),
          account.getPassword(),
          account.getActive(),
          true,
          true,
          true,
          authorities
      );
   }

   @Transactional
   public void register(Account account) {
      accountDAO.findByUserName(account.getUserName()).ifPresent(e ->
      {throw new DeliveryError("Account with that Username already exists");});
      accountDAO.addAccount(account
          .withPassword(passwordEncoder.encode(account.getPassword()))
          .withActive(true),
          Role.builder().roleName(ACCOUNT).build());
   }
}