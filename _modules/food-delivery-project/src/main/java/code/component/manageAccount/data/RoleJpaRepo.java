package code.component.manageAccount.data;

import code.component.manageAccount.domain.Role;
import code.component.manageAccount.domain.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleJpaRepo extends JpaRepository<RoleEntity, Integer> {
   Optional<RoleEntity> findByRoleName(Role.ACCOUNT_ROLE roleName);
}