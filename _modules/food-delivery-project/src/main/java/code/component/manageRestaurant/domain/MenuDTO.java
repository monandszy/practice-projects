package code.component.manageRestaurant.domain;

import jakarta.validation.constraints.NotNull;
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
public class MenuDTO {

   private Integer id;
   @NotNull
   private Menu.MenuType menuType;

}