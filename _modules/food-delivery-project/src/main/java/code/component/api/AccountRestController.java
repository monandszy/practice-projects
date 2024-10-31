package code.component.api;

import code.component.manageAccount.LoginService;
import code.component.manageAccount.domain.AccountDTO;
import code.component.manageAccount.domain.mapper.AccountDTOMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AccountRestController {

   private AccountDTOMapper accountDTOMapper;
   private LoginService loginService;

   public static final String API_REGISTER = "api/register";

   @PostMapping(value = API_REGISTER)
   public ResponseEntity<?> processRegister(
       @Valid @RequestBody AccountDTO account
   ) {
      loginService.register(accountDTOMapper.mapFromDTO(account));
      return ResponseEntity.status(HttpStatus.CREATED).build();
   }
}