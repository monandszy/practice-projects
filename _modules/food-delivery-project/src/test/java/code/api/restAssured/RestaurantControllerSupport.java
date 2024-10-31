package code.api.restAssured;

import code.component.api.restaurantApi.OpenRestaurantDTO;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static code.component.api.restaurantApi.RestaurantRestController.RESTAURANT_OPEN;

public interface RestaurantControllerSupport {

   RequestSpecification requestSpecification();

   default OpenRestaurantDTO openRestaurant(OpenRestaurantDTO restaurantDTO) {
      return requestSpecification()
          .body(restaurantDTO)
          .post(RESTAURANT_OPEN)
          .then()
//          .statusCode(HttpStatus.OK.value())
          .contentType(ContentType.JSON)
          .and()
          .extract()
          .as(OpenRestaurantDTO.class);
   }

}