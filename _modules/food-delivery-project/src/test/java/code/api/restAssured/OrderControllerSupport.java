package code.api.restAssured;

import code.component.api.orderApi.OrderDTOs;
import code.component.api.orderApi.OrderInputDTO;
import code.component.api.orderApi.OrderPositionDTOs;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

import static code.component.api.orderApi.OrderRestController.ORDER_ADD;
import static code.component.api.orderApi.OrderRestController.ORDER_DELETE;
import static code.component.api.orderApi.OrderRestController.ORDER_GET;
import static code.component.api.orderApi.OrderRestController.ORDER_GET_DETAILS;

public interface OrderControllerSupport {

  RequestSpecification requestSpecification();

  default OrderDTOs getOrders(String clientId) {
     return requestSpecification()
         .get(ORDER_GET, clientId)
         .then()
         .statusCode(HttpStatus.OK.value())
         .and()
         .extract()
         .as(OrderDTOs.class);
  }

   default OrderPositionDTOs getOrderDetails(Integer orderId) {
      return requestSpecification()
          .get(ORDER_GET_DETAILS, orderId)
          .then()
          .statusCode(HttpStatus.OK.value())
          .and()
          .extract()
          .as(OrderPositionDTOs.class);
   }

   default ExtractableResponse<Response> addOrder(OrderInputDTO orderInputDTO) {
      return requestSpecification()
          .body(orderInputDTO)
          .post(ORDER_ADD)
          .then()
          .statusCode(HttpStatus.CREATED.value())
          .and()
          .extract();
   }

   default ExtractableResponse<Response> cancelOrder(Integer orderId) {
      return requestSpecification()
          .delete(ORDER_DELETE, orderId)
          .then()
          .statusCode(HttpStatus.OK.value())
          .and()
          .extract();
   }

}