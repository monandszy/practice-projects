package code.api.restAssured;

import code.component.manageAccount.domain.AccountDTO;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static code.component.api.AccountRestController.API_REGISTER;

public interface AccountControllerSupport {

   RequestSpecification requestSpecification();

   default ExtractableResponse<Response> register(AccountDTO accountDTO) {
      return requestSpecification()
          .body(accountDTO)
          .post(API_REGISTER)
          .then()
//          .statusCode(HttpStatus.CREATED.value())
          .and()
          .extract();
   }
}