package code.component.manageAccount.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;

@With
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

   private Integer id;
   @NotEmpty
   private String userName;
   @NotEmpty
   private String password;
   private Boolean active;
   private String roles;
}