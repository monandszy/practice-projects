package code.infrastructure.configuration;

import code.infrastructure.database.entity.EmployeeEntity;
import code.infrastructure.database.repositories.EmployeeRepository;
import code.infrastructure.database.repositories.jpa.EmployeeJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class BootstrapApplicationComponent implements ApplicationListener<ContextRefreshedEvent> {

    private EmployeeJpaRepository employeeRepository;

    @Override
    public void onApplicationEvent(@NonNull  ContextRefreshedEvent event) {
        employeeRepository.deleteAll();
        employeeRepository.save(EmployeeEntity.builder().name("1st").surname("2nd").salary(new BigDecimal("0.01")).build());
    }
}