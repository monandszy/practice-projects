package code.component.manageRestaurant.manageDelivery;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DistanceCalculationService {

   public static final double EARTH_RADIUS = 6371;

   // https://www.baeldung.com/java-find-distance-between-points
   // I used the least accurate version to save on compute
   public Double calculateDistance(
       Double startLon,
       Double startLat,
       Double endLon,
       Double endLat
   ) {
      double dLat = Math.toRadians((endLat - startLat));
      double dLong = Math.toRadians((endLon - startLon));

      Double radStartLat = Math.toRadians(startLat);
      Double radEndLat = Math.toRadians(endLat);

      double a = haversine(dLat) + Math.cos(radStartLat) * Math.cos(radEndLat) * haversine(dLong);
      double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

      return EARTH_RADIUS * c;
   }

   double haversine(double val) {
      return Math.pow(Math.sin(val / 2), 2);
   }
}