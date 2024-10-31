package code.infrastructure.database.repositories.jpa;

import code.infrastructure.database.entity.EmployeeEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeJpaRepository extends JpaRepository<EmployeeEntity, Integer> {
}