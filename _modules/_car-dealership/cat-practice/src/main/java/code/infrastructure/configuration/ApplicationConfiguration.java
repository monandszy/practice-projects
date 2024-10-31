package code.infrastructure.configuration;

import code._ComponentScanMarker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackageClasses = {_ComponentScanMarker.class})
@Import({WebMvcConfiguration.class, PersistenceJpaConfiguration.class})
public class ApplicationConfiguration {
}