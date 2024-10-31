package code.component.manageRestaurant.manageDelivery.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id"})
public class Address {

   Integer id;
   String city;
   String postalCode;
   String ipAddress;
   Double latitude;
   Double longitude;

}