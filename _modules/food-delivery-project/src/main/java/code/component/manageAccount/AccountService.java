package code.component.manageAccount;

import code.component.manageAccount.domain.Account;
import code.component.manageAccount.domain.Role;
import code.configuration.Constants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {

   private final AccountDAO accountDAO;
   private final HttpServletRequest request;
   private String localAddressPattern = "(^127\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$)|(^10\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$)|(^172\\.1[6-9]{1}[0-9]{0,1}\\.[0-9]{1,3}\\.[0-9]{1,3}$)|(^172\\.2[0-9]{1}[0-9]{0,1}\\.[0-9]{1,3}\\.[0-9]{1,3}$)|(^172\\.3[0-1]{1}[0-9]{0,1}\\.[0-9]{1,3}\\.[0-9]{1,3}$)|(^192\\.168\\.[0-9]{1,3}\\.[0-9]{1,3}$)";

   public String getAuthenticatedUserName() {
      try {
         return SecurityContextHolder.getContext().getAuthentication().getName();
      } catch (Exception e) {
         return "anonymousUser";
      }
   }

   public String getCurrentIp() {
      String remoteAddr = "";
      request.getRemoteAddr();
      if (Objects.nonNull(request)) {
         remoteAddr = request.getHeader("X-FORWARDED-FOR");
         if (remoteAddr == null || "".equals(remoteAddr)) {
            remoteAddr = request.getRemoteAddr();
         }
      }
      if (remoteAddr.matches(localAddressPattern) || Objects.isNull(remoteAddr)) {
         if (log.isErrorEnabled()) {
            log.error("Access from privateAddress: [%s], using test ip as backup".formatted(remoteAddr));
         }
         return Constants.TEST_IP;
      } else {
         return remoteAddr;
      }
   }

   public List<Account> getAccountPage(Integer pageNumber) {
      return accountDAO.getAccountPage(pageNumber);
   }

   public void deleteAccount(String userName) {
      accountDAO.deleteByUserName(userName);
   }

   public void setRole(String userName, Role role) {
      accountDAO.setRole(userName, role);
   }
}