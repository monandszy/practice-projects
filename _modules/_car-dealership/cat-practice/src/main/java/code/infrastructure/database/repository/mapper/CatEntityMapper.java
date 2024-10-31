package code.infrastructure.database.repository.mapper;

import code.business.domain.Cat;
import code.infrastructure.database.entity.CatEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CatEntityMapper {

//    Cat mapFromEntity(CatEntity entity);

    CatEntity mapToEntity(Cat request);
}