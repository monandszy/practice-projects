package code.component.manageRestaurant.domain;

import code.component.manageRestaurant.manageImages.ImageIdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu_position", schema = "food_delivery")
public class MenuPositionEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Integer id;

   @Column(name = "price")
   private BigDecimal price;

   @Column(name = "name")
   private String name;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "menu_id")
   private MenuEntity menu;

   @OneToMany(fetch = FetchType.EAGER, mappedBy = "menuPosition")
   private List<ImageIdEntity> images;

}