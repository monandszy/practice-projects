package code.restaurant.web;

import code.component.manageRestaurant.domain.MenuPositionDTO;
import code.component.manageRestaurant.domain.mapper.RestaurantDTOMapper;
import code.component.manageRestaurant.service.MenuPositionService;
import code.component.manageRestaurant.web.sellerInput.SellerMenuController;
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

import static code.component.manageRestaurant.web.sellerInput.SellerMenuController.MY_MENU;
import static code.component.manageRestaurant.web.sellerInput.SellerMenuController.MY_MENU_ADD;
import static code.component.manageRestaurant.web.sellerInput.SellerMenuController.MY_MENU_DELETE;
import static code.component.manageRestaurant.web.sellerInput.SellerMenuController.MY_MENU_GET;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = SellerMenuController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@AutoConfigureMockMvc(addFilters = false)
public class SellerMenuWebIT {

   private MockMvc mockMvc;

   @MockBean
   private MenuPositionService menuPositionService;

   @MockBean
   private RestaurantDTOMapper dtoMapper;

   @Test
   void testGet() throws Exception {
      Integer menuId = 1;
      Integer expectedPageNumber = 0;
      List<MenuPositionDTO> menuPage = List.of(WebFixtures.getMenuPositionDTO());
      Mockito.when(dtoMapper.mapMPToDTOList(any())).thenReturn(menuPage);
      mockMvc.perform(get(Constants.URL +MY_MENU_GET, menuId))
          .andExpect(status().isOk())
          .andExpect(model().attribute("pageNumber", expectedPageNumber))
          .andExpect(model().attribute("menuPage", menuPage))
          .andExpect(view().name("seller/" + MY_MENU));
      Mockito.verify(menuPositionService).getPageByMenu(menuId, expectedPageNumber);
   }

   @Test
   void testAdd() throws Exception {
      Integer menuId = 1;
      MenuPositionDTO menuPositionDTO = WebFixtures.getMenuPositionDTO();
      mockMvc.perform(post(Constants.URL + MY_MENU_ADD)
              .param("menuId", menuId.toString())
              .flashAttr("menuPositionDTO", menuPositionDTO))
          .andExpect(redirectedUrl("/"
              + MY_MENU_GET.replace("{menuId}", menuId.toString())))
          .andExpect(status().isFound());
      Mockito.verify(menuPositionService).add(null, null, menuId);
   }

   @Test
   void testDelete() throws Exception {
      Integer menuId = 1;
      Integer menuPositionId = 1;
      mockMvc.perform(post(Constants.URL +MY_MENU_DELETE, menuPositionId)
              .flashAttr("menuId", menuId.toString()))
          .andExpect(redirectedUrl("/"
              + MY_MENU_GET.replace("{menuId}", menuId.toString())))
          .andExpect(status().isFound());
      Mockito.verify(menuPositionService).deleteById(menuPositionId);
   }
}