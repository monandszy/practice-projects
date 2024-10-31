package code.component.manageRestaurant.manageDelivery.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class AddressDTO {

   private Integer id;
   @NotEmpty
   private String city;
   @NotEmpty
   private String postalCode;
   @Pattern(regexp = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$")
   private String ipAddress;
   @NotNull
   private Double latitude;
   @NotNull
   private Double longitude;
}