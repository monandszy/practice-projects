package code.component.manageAccount.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

   private Integer id;
   private Role.ACCOUNT_ROLE roleName;
}