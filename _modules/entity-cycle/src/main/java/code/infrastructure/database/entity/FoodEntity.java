package code.infrastructure.database.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@Getter
@Setter
@ToString(exclude = "creature")
@EqualsAndHashCode(exclude = "creature")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "food", schema = "entity_cycle")
public class FoodEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "food_id", unique = true, nullable = false)
   private Integer id;

   @Column(name = "nutritional_value")
   private Integer nutritionalValue;

   @Column(name = "description")
   private String description;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "creature_id")
   private CreatureEntity creature;
}