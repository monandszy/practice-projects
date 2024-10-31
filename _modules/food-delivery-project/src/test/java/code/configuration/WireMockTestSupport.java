package code.configuration;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import java.util.Map;

public interface WireMockTestSupport {

   default void stubForIp(final WireMockServer wireMockServer, String ip) {
      wireMockServer.stubFor(WireMock.get(
              WireMock.urlPathEqualTo("/v1/"))
          .willReturn(WireMock.aResponse()
                  .withHeader("Content-Type", "application/json")
                  .withBodyFile("inlineResponse200_1.json")
                  .withTransformerParameters(Map.of("ip", ip))
                  .withTransformers("response-template")));
   }

   /*
   * TODO
   * org.springframework.web.reactive.function.client.WebClientResponseException$InternalServerError: 500 Internal Server Error from GET http://localhost:9090/v1/
   *  Suspected couse - incompatiblity with "Mono"
   * Create a proxy controller and mock that heh?
   *
   * Maybe restore hidden method via manual configuration?
   * */
}