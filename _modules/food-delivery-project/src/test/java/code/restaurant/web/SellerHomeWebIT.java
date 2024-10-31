package code.restaurant.web;

import code.component.manageAccount.AccountService;
import code.component.manageRestaurant.web.sellerInput.SellerHomeController;
import code.configuration.Constants;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static code.component.manageRestaurant.web.sellerInput.SellerHomeController.MANAGE;

@WebMvcTest(controllers = SellerHomeController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@AutoConfigureMockMvc(addFilters = false)
public class SellerHomeWebIT {

   private MockMvc mockMvc;

   @MockBean
   private AccountService accountService;

   @Test
   void testGet() throws Exception {
      String userName = "seller";
      Mockito.when(accountService.getAuthenticatedUserName()).thenReturn(userName);
      mockMvc.perform(MockMvcRequestBuilders.get(Constants.URL + MANAGE))
          .andExpect(MockMvcResultMatchers.view().name("seller/" + MANAGE))
          .andExpect(MockMvcResultMatchers.model().attribute(Constants.USERNAME, userName));
   }


}