package code.infrastructure.configuration;

import code.infrastructure.database.entity._EntityMarker;
import code.infrastructure.database.repositories.jpa._JpaRepositoriesMarker;
import com.zaxxer.hikari.HikariConfig;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

//@Configuration
//@AllArgsConstructor
//@EnableTransactionManagement
//@PropertySource("classpath:database.properties")
//@EnableJpaRepositories(basePackageClasses = _JpaRepositoriesMarker.class)
public class PersistenceJpaConfiguration {
//    private final org.springframework.core.env.Environment env;
//
//    @Bean
//    @DependsOn("flyway")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactoryBean.setDataSource(dataSource());
//        entityManagerFactoryBean.setPackagesToScan(_EntityMarker.class.getPackageName());
//        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        entityManagerFactoryBean.setJpaProperties(jpaProperties());
//        return entityManagerFactoryBean;
//    }
//
//    @Bean
//    public DataSource dataSource() {
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setDriverClassName("org.postgresql.Driver");
//        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/dealership");
//        hikariConfig.setUsername("postgres");
//        hikariConfig.setPassword("meow");
//
//        hikariConfig.setConnectionTestQuery("SELECT 1");
//        hikariConfig.setPoolName("springHikariCP");
//
//        hikariConfig.setMaximumPoolSize(20);
//        hikariConfig.setConnectionTimeout(20000);
//        hikariConfig.setMinimumIdle(10);
//        hikariConfig.setIdleTimeout(300000);
//
//        return hikariConfig.getDataSource();
//    }
//
//    public Properties jpaProperties() {
//        final Properties properties = new Properties();
//        properties.setProperty(Environment.DIALECT, env.getProperty(Environment.DIALECT));
//        properties.setProperty(Environment.HBM2DDL_AUTO, env.getProperty(Environment.HBM2DDL_AUTO));
//        properties.setProperty(Environment.SHOW_SQL, env.getProperty(Environment.SHOW_SQL));
//        properties.setProperty(Environment.FORMAT_SQL, env.getProperty(Environment.FORMAT_SQL));
//        return properties;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(
//            final EntityManagerFactory entityManagerFactory
//    ) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory);
//        return transactionManager;
//    }
//
//    @Bean
//    public static PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
//        return new PersistenceExceptionTranslationPostProcessor();
//    }
//
//    @Bean(initMethod = "migrate")
//    Flyway flyway() {
//        ClassicConfiguration configuration = new ClassicConfiguration();
//        configuration.setBaselineOnMigrate(true);
//        configuration.setLocations(new Location("classpath:db/migrations"));
//        configuration.setDataSource(dataSource());
//        return new Flyway(configuration);
//    }
}