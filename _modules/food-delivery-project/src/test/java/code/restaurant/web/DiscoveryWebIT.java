package code.restaurant.web;

import code.component.api.ipAddressApi.ApiClientImpl;
import code.component.manageAccount.AccountService;
import code.component.manageRestaurant.domain.RestaurantDTO;
import code.component.manageRestaurant.domain.mapper.RestaurantDTOMapper;
import code.component.manageRestaurant.manageDelivery.AddressService;
import code.component.manageRestaurant.manageDelivery.domain.Address;
import code.component.manageRestaurant.manageDelivery.domain.AddressDTOMapper;
import code.component.manageRestaurant.web.clientOutput.DiscoverController;
import code.configuration.Constants;
import code.util.DataFixtures;
import code.util.WebFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static code.component.manageRestaurant.web.clientOutput.DiscoverController.DISCOVER;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = {
    DiscoverController.class,
})
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DiscoveryWebIT {

   private MockMvc mockMvc;

   @MockBean
   private AddressService addressService;
   @MockBean
   private RestaurantDTOMapper dtoMapper;
   @MockBean
   @SuppressWarnings("unused")
   private AddressDTOMapper addressDTOMapper;
   @MockBean
   private AccountService accountService;
   @MockBean
   @SuppressWarnings("unused")
   private ApiClientImpl apiClient;

   @Test
   void testGetRestaurantsByIp() throws Exception {
      Integer pageNumber = 2;
      String userId = "test";
      List<RestaurantDTO> restaurantPage = List.of(WebFixtures.getRestaurantDTO());
      Address address = DataFixtures.getAddress();
      Mockito.when(accountService.getAuthenticatedUserName()).thenReturn(userId);
      Mockito.when(addressService.getAddress(null)).thenReturn(address);
      Mockito.when(dtoMapper.mapRToDTOList(any())).thenReturn(restaurantPage);
      mockMvc.perform(get(Constants.URL + DISCOVER)
              .param("pageNumber", pageNumber.toString()))
          .andExpect(status().isOk())
          .andExpect(request().sessionAttribute(Constants.ADDRESS, address))
          .andExpect(model().attribute(Constants.USERNAME, userId))
          .andExpect(model().attribute("pageNumber", pageNumber))
          .andExpect(model().attribute("restaurantsByAddressPage", restaurantPage))
          .andExpect(view().name("client/" + DISCOVER));
      Mockito.verify(addressService).getPageByAddress(address, pageNumber);
      Mockito.verify(addressService).getAddress(null);
   }
}