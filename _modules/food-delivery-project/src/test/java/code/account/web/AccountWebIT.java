package code.account.web;

import code.component.manageAccount.AccountService;
import code.component.manageAccount.domain.AccountDTO;
import code.component.manageAccount.domain.Role;
import code.component.manageAccount.domain.RoleDTO;
import code.component.manageAccount.domain.mapper.AccountDTOMapper;
import code.component.manageAccount.web.HomeController;
import code.configuration.Constants;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static code.component.manageAccount.web.HomeController.ACCOUNTS;
import static code.component.manageAccount.web.HomeController.ACCOUNTS_DELETE;
import static code.component.manageAccount.web.HomeController.ACCOUNTS_SET_ROLE;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = HomeController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AccountWebIT {

   private MockMvc mockMvc;

   @MockBean
   private AccountService accountService;
   @MockBean
   private AccountDTOMapper accountDTOMapper;

   @Test
   void testGetAccounts() throws Exception {
      List<AccountDTO> accounts = List.of(AccountDTO.builder().id(1).build());
      Mockito.when(accountDTOMapper.mapAToDTOList(any())).thenReturn(accounts);
      mockMvc.perform(get(Constants.URL + ACCOUNTS))
          .andExpect(model().attribute("accountPage", accounts))
          .andExpect(model().attribute("pageNumber", 0))
          .andExpect(view().name("home"));
      Mockito.verify(accountService).getAccountPage(0);
   }

   @Test
   void testSetRole() throws Exception {
      String userName = "test";
      RoleDTO role = RoleDTO.builder().roleName(Role.ACCOUNT_ROLE.ACCOUNT).build();
      mockMvc.perform(post(Constants.URL + ACCOUNTS_SET_ROLE, userName)
              .flashAttr("role", role))
          .andExpect(redirectedUrl("/home"));
      Mockito.verify(accountService).setRole(userName, null);
   }

   @Test
   void testDeleteAccount() throws Exception {
      String userName = "test";
      mockMvc.perform(post(Constants.URL + ACCOUNTS_DELETE, userName))
          .andExpect(redirectedUrl("/home"));
      Mockito.verify(accountService).deleteAccount(userName);
   }
}