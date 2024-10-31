package code.component.manageRestaurant.manageDelivery.domain;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.stereotype.Component;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class IpDTO {

   @Pattern(regexp = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$")
   private String ip;
}