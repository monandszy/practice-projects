package code.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

//@TestConfiguration
public class DatabaseContainerTestConfiguration {
   public static final String USERNAME = "test";
   public static final String PASSWORD = "test";
   public static final String CONTAINER = "postgres:16.1";

   @Bean
   PostgreSQLContainer<?> postgresqlContainer() {
    return container;
   }

   @ServiceConnection
   static PostgreSQLContainer<?> container = new PostgreSQLContainer<>(CONTAINER);

   @Bean
   DataSource dataSource(final PostgreSQLContainer<?> postgresqlContainer) {
      return DataSourceBuilder.create()
          .type(HikariDataSource.class)
          .driverClassName(postgresqlContainer.getDriverClassName())
          .url(postgresqlContainer.getJdbcUrl())
          .username(postgresqlContainer.getUsername())
          .password(postgresqlContainer.getPassword())
          .build();
   }

   @Bean
   public ServletWebServerFactory servletWebServerFactory() {
      return new TomcatServletWebServerFactory();
   }
}