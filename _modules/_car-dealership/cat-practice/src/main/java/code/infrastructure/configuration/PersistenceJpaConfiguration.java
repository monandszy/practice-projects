package code.infrastructure.configuration;

import code.infrastructure.database.entity._EntityMarker;
import code.infrastructure.database.repository.jpa._JpaRepositoriesMarker;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = {_JpaRepositoriesMarker.class})
@PropertySource({"classpath:database.properties"})
@AllArgsConstructor
public class PersistenceJpaConfiguration {

   private final Environment environment;

   @Bean
   @DependsOn("flyway")
   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
      final LocalContainerEntityManagerFactoryBean entityManagerFactory
              = new LocalContainerEntityManagerFactoryBean();
      entityManagerFactory.setDataSource(dataSource());
      entityManagerFactory.setPackagesToScan(_EntityMarker.class.getPackageName());
      entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
      entityManagerFactory.setJpaProperties(jpaProperties());
      return entityManagerFactory;
   }

   final Properties jpaProperties() {
      final Properties hibernateProperties = new Properties();
      hibernateProperties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
      hibernateProperties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
      hibernateProperties.setProperty("hibernate.format_sql", environment.getProperty("hibernate.format_sql"));
      return hibernateProperties;
   }

   @Bean
   public DataSource dataSource() {
      final DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName("org.postgresql.Driver");
      dataSource.setUrl("jdbc:postgresql://localhost:5432/java_model");
      dataSource.setUsername("postgres");
      dataSource.setPassword("postgres");
      dataSource.setSchema("web_mvc");
      return dataSource;
   }
//   @Bean
//   public DataSource dataSource() {
//      final HikariConfig hikariConfig = new HikariConfig();
//      hikariConfig.setDriverClassName(Objects.requireNonNull(environment.getProperty("jdbc.driverClassName")));
//      hikariConfig.setJdbcUrl(environment.getProperty("jdbc.url"));
//      hikariConfig.setUsername(environment.getProperty("jdbc.user"));
//      hikariConfig.setPassword(environment.getProperty("jdbc.pass"));
//      hikariConfig.setSchema(environment.getProperty("jdbc.schema"));
//
//      hikariConfig.setConnectionTestQuery("SELECT 1");
//      hikariConfig.setPoolName("springHikariCP");
//
//      hikariConfig.setMaximumPoolSize(20);
//      hikariConfig.setConnectionTimeout(20000);
//      hikariConfig.setMinimumIdle(10);
//      hikariConfig.setIdleTimeout(300000);
//
//      return new HikariDataSource(hikariConfig);
//   }
   @Bean
   public PlatformTransactionManager transactionManager(
           final EntityManagerFactory entityManagerFactory
   ) {
      final JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager.setEntityManagerFactory(entityManagerFactory);
      return transactionManager;
   }

   @Bean
   public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
      return new PersistenceExceptionTranslationPostProcessor();
   }

   @Bean(initMethod = "migrate")
   @DependsOn(value = "dataSource")
   Flyway flyway() {
      ClassicConfiguration configuration = new ClassicConfiguration();
      configuration.setBaselineOnMigrate(true);
      configuration.setLocations(new Location("classpath:database/migrations"));
      configuration.setDataSource(dataSource());
      return new Flyway(configuration);
   }
}