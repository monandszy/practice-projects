package code.infrastructure.configuration;

import code.infrastructure.database.entity.CatEntity;
import code.infrastructure.database.repository.jpa.CatJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Component
@AllArgsConstructor
public class BootstrapApplicationComponent implements ApplicationListener<ContextRefreshedEvent> {

    private CatJpaRepository catJpaRepository;

    @Override
    @Transactional
    public void onApplicationEvent(final @NonNull ContextRefreshedEvent event) {
        catJpaRepository.deleteAll();

        catJpaRepository.save(CatEntity.builder()
            .name("Stefan")
            .salary(new BigDecimal("52322.00"))
            .build());
        catJpaRepository.save(CatEntity.builder()
            .name("Agnieszka")
            .salary(new BigDecimal("62341.00"))
            .build());
        catJpaRepository.save(CatEntity.builder()
            .name("Tomasz")
            .salary(new BigDecimal("53231.00"))
            .build());
    }
}