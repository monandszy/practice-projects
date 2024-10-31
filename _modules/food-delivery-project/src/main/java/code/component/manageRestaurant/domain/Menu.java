package code.component.manageRestaurant.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.With;

import java.util.List;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id"})
public class Menu {

   Integer id;
   MenuType menuType;
   List<MenuPosition> menuPositions;
   Restaurant restaurant;

   public enum MenuType {
      MENU_TYPE1,
      MENU_TYPE2,
      MENU_TYPE3
   }
}