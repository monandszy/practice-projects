package code.infrastructure.database.repository.jpa;

import code.infrastructure.database.entity.CatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatJpaRepository extends JpaRepository<CatEntity, Integer> {
}