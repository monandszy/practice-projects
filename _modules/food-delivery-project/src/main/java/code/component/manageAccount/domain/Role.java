package code.component.manageAccount.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
@EqualsAndHashCode
@ToString
public class Role {

   Integer id;
   ACCOUNT_ROLE roleName;

   public enum ACCOUNT_ROLE {
      ACCOUNT,
      SELLER,
      CLIENT,
      ADMIN
   }
}