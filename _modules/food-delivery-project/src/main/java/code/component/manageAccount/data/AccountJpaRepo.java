package code.component.manageAccount.data;

import code.component.manageAccount.domain.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountJpaRepo extends JpaRepository<AccountEntity, Integer> {
   @EntityGraph(
       attributePaths = {
           "roles"
       }
   )
   Optional<AccountEntity> findByUserName(String username);

   @Override
   @EntityGraph(
       attributePaths = {
           "roles"
       }
   )
   Page<AccountEntity> findAll(Pageable pageable);
}