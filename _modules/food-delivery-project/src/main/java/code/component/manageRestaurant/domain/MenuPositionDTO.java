package code.component.manageRestaurant.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.util.List;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuPositionDTO {

   private Integer id;
   @NotNull
   @Range(min = 10, max = 100000)
   private BigDecimal price;
   @NotEmpty
   private String name;
   private List<Integer> images;
}