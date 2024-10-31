package code.configuration;

import code.openApi.ApiClient;
import code.openApi.infrastructure.DefaultApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApiConfig {

   @Value("${api.abstract.url}")
   private String abstractUrl;

   @Bean
   public ApiClient apiClient(WebClient webClient) {
      ApiClient apiClient = new ApiClient(webClient);
      apiClient.setBasePath(abstractUrl);
      return apiClient;
   }

   @Bean
   public DefaultApi petApi(final ApiClient apiClient) {
      return new DefaultApi(apiClient);
   }

}