package code.component.api.ipAddressApi;

import code.component.manageRestaurant.manageDelivery.domain.Address;
import code.configuration.Constants;
import code.openApi.infrastructure.DefaultApi;
import code.openApi.model.InlineResponse200;
import code.web.exception.DeliveryError;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class ApiClientImpl implements ApiDAO {

   private AddressApiMapper addressApiMapper;
   private DefaultApi defaultApi;

   public Address getAddressFromApi(String ip) {
      try {
         Mono<InlineResponse200> inlineResponse200Mono = defaultApi.v1Get(Constants.TOKEN, ip, Constants.FIELDS);
         InlineResponse200 block = inlineResponse200Mono.block();
         return addressApiMapper.mapToAddress(block);
      } catch (Exception ex ) {
         log.error(ex.getMessage());
         throw new DeliveryError("Exception while connecting to ipApi: [%s] [%s]".formatted(
             ex.getMessage(), ex.getClass()
         ));
      }
   }
}