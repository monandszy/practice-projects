package code.component.manageRestaurant.manageDelivery.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address", schema = "food_delivery")
public class AddressEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name ="id")
   private Integer id;

   @Column(name = "city")
   private String city;
   @Column(name = "postal_code")
   private String postalCode;
   @Column(name = "ip_address")
   private String ipAddress;
   @Column(name = "latitude")
   private Double latitude;
   @Column(name = "longitude")
   private Double longitude;
}