package code.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

   public static final int TIMEOUT = 5000;


   @Override
   public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/").setViewName("home");
   }

   @Bean
   public WebClient webClient(final ObjectMapper objectMapper) {
      var httpClient = HttpClient.create()
          .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
          .responseTimeout(Duration.ofMillis(TIMEOUT))
          .doOnConnected(conn ->
              conn.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS))
                  .addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS)));

      var exchangeServices = ExchangeStrategies
          .builder()
          .codecs(c -> {
             c.defaultCodecs()
                 .jackson2JsonEncoder(
                     new Jackson2JsonEncoder(
                         objectMapper,
                         MediaType.APPLICATION_JSON
                     ));
             c.defaultCodecs()
                 .jackson2JsonDecoder(
                     new Jackson2JsonDecoder(
                         objectMapper,
                         MediaType.APPLICATION_JSON
                     ));
          }).build();

      return WebClient.builder()
          .exchangeStrategies(exchangeServices)
          .clientConnector(new ReactorClientHttpConnector(httpClient))
          .build();
   }

}