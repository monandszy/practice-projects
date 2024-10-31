package code.restaurant.web;

import code.component.manageAccount.AccountService;
import code.component.manageRestaurant.domain.MenuDTO;
import code.component.manageRestaurant.domain.mapper.RestaurantDTOMapper;
import code.component.manageRestaurant.service.MenuService;
import code.component.manageRestaurant.service.RestaurantService;
import code.component.manageRestaurant.web.sellerInput.SellerRestaurantController;
import code.configuration.Constants;
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

import static code.component.manageRestaurant.web.sellerInput.SellerRestaurantController.MY_RESTAURANT;
import static code.component.manageRestaurant.web.sellerInput.SellerRestaurantController.MY_RESTAURANT_ADD;
import static code.component.manageRestaurant.web.sellerInput.SellerRestaurantController.MY_RESTAURANT_DELETE;
import static code.component.manageRestaurant.web.sellerInput.SellerRestaurantController.MY_RESTAURANT_GET;
import static code.component.manageRestaurant.web.sellerInput.SellerRestaurantController.MY_RESTAURANT_UPDATE;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = SellerRestaurantController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@AutoConfigureMockMvc(addFilters = false)
public class SellerRestaurantWebIT {

   private MockMvc mockMvc;

   @MockBean
   private MenuService menuService;

   @MockBean
   private RestaurantService restaurantService;

   @MockBean
   private AccountService accountService;

   @MockBean
   private RestaurantDTOMapper dtoMapper;

   @Test
   void testGet() throws Exception {
      Integer restaurantId = 1;
      Integer expectedPageNumber = 0;
      List<MenuDTO> restaurantPage = List.of(WebFixtures.getMenuDTO());
      Mockito.when(dtoMapper.mapMToDTOList(any())).thenReturn(restaurantPage);
      mockMvc.perform(get(Constants.URL + MY_RESTAURANT_GET, restaurantId))
          .andExpect(status().isOk())
          .andExpect(request().sessionAttribute(Constants.RESTAURANT, restaurantId))
          .andExpect(model().attribute("menuDTO", new MenuDTO()))
          .andExpect(model().attribute("restaurantId", restaurantId))
          .andExpect(model().attribute("pageNumber", expectedPageNumber))
          .andExpect(model().attribute("restaurantPage", restaurantPage))
          .andExpect(view().name("seller/" + MY_RESTAURANT));
      Mockito.verify(menuService).getPageByRestaurant(restaurantId, expectedPageNumber);
   }

   @Test
   void testAdd() throws Exception {
      Integer restaurantId = 1;
      MenuDTO menuDTO = WebFixtures.getMenuDTO();
      mockMvc.perform(post(Constants.URL + MY_RESTAURANT_ADD)
              .sessionAttr(Constants.RESTAURANT, restaurantId)
              .flashAttr("menuDTO", menuDTO))
          .andExpect(redirectedUrl("/"
              + MY_RESTAURANT_GET.replace("{restaurantId}", restaurantId.toString())))
          .andExpect(status().isFound());
      Mockito.verify(menuService).add(null, restaurantId);
   }

   @Test
   void testDelete() throws Exception {
      Integer restaurantId = 1;
      Integer menuId = 1;
      mockMvc.perform(post(Constants.URL + MY_RESTAURANT_DELETE, menuId)
              .sessionAttr(Constants.RESTAURANT, restaurantId))
          .andExpect(redirectedUrl("/"
              + MY_RESTAURANT_GET.replace("{restaurantId}", restaurantId.toString())))
          .andExpect(status().isFound());
      Mockito.verify(menuService).deleteById(menuId);
   }

   @Test
   void testUpdateRestaurant() throws Exception {
      Integer restaurantId = 1;
      Double deliveryRange = 2D;
      mockMvc.perform(post(Constants.URL + MY_RESTAURANT_UPDATE, restaurantId)
              .param("deliveryRange", deliveryRange.toString()))
          .andExpect(redirectedUrl("/"
              + MY_RESTAURANT_GET.replace("{restaurantId}", restaurantId.toString())))
          .andExpect(status().isFound());
      Mockito.verify(restaurantService).update(restaurantId, deliveryRange);
   }
}