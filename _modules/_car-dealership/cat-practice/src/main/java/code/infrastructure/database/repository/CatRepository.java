package code.infrastructure.database.repository;

import code.business.dao.CatDAO;
import code.infrastructure.database.repository.jpa.CatJpaRepository;
import code.infrastructure.database.repository.mapper.CatEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CatRepository implements CatDAO {

    private final CatJpaRepository catJpaRepository;
    private final CatEntityMapper catEntityMapper;
}