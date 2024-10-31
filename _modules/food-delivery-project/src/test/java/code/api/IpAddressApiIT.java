package code.api;

import code.configuration.AbstractSpringBootIT;
import code.configuration.Constants;
import code.openApi.infrastructure.DefaultApi;
import code.openApi.model.InlineResponse200;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import reactor.core.publisher.Mono;

@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class IpAddressApiIT extends AbstractSpringBootIT {

   private DefaultApi defaultApi;

   @Test
   public void testAbstractApiConnection() {
      Mono<InlineResponse200> inlineResponse200Mono = defaultApi.v1Get(
          Constants.TOKEN, Constants.TEST_IP, Constants.FIELDS);
      InlineResponse200 block = inlineResponse200Mono.block();
      Assertions.assertNotNull(block.getIpAddress());
      Assertions.assertNotNull(block.getCity());
      Assertions.assertNotNull(block.getPostalCode());
      Assertions.assertNotNull(block.getLongitude());
      Assertions.assertNotNull(block.getLatitude());
   }
}