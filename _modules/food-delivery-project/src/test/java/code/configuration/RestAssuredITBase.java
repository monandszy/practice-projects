package code.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ClasspathFileSource;
import com.github.tomakehurst.wiremock.common.FileSource;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@ActiveProfiles("test")
public abstract class RestAssuredITBase
    extends AbstractSpringBootIT {

   @LocalServerPort
   private int serverPort;

   @Value("${server.servlet.context-path}")
   private String basePath;

   @Autowired
   @Getter
   protected ObjectMapper objectMapper;

   protected static WireMockServer wireMockServer;

   @BeforeAll
   static void beforeAll() {
      FileSource fs = new ClasspathFileSource("src/test/resources/wiremock/");
      wireMockServer = new WireMockServer(
          wireMockConfig()
              .port(9090)
              .fileSource(fs)
              .globalTemplating(true));
      wireMockServer.start();
   }
   @AfterEach
   void afterEach() {
      wireMockServer.resetAll();
   }
   @AfterAll
   static void afterAll() {
      wireMockServer.stop();
   }

   public RequestSpecification requestSpecification() {
      return RestAssured
          .given()
          .config(getConfig())
          .basePath(basePath)
          .port(serverPort)
          .accept(ContentType.JSON)
          .contentType(ContentType.JSON);
   }
   private RestAssuredConfig getConfig() {
      return RestAssuredConfig
          .config()
          .objectMapperConfig(new ObjectMapperConfig()
              .jackson2ObjectMapperFactory((type, s) -> objectMapper));
   }
}