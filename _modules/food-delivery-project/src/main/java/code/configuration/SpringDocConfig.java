package code.configuration;

import code.ApplicationRunner;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

   @Bean
   public GroupedOpenApi groupedOpenApi() {
      return GroupedOpenApi.builder()
          .group("default")
          .pathsToMatch("/**")
          .packagesToScan(ApplicationRunner.class.getPackageName())
          .build();
   }

   @Bean
   public OpenAPI springDocOpenApi() {
      return new OpenAPI()
          .components(new Components())
          .info(new Info()
              .title("Food Delivery App")
              .contact(contact())
              .version("1.0"));
   }

   private Contact contact() {
      return new Contact()
          .email("monandszy@gmail.com");
   }
}