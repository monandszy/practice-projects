package code.component.manageAccount.domain.mapper;

import code.component.manageAccount.domain.Account;
import code.component.manageAccount.domain.AccountEntity;
import code.component.manageAccount.domain.Role;
import code.component.manageAccount.domain.RoleEntity;
import code.configuration.Generated;
import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@AnnotateWith(Generated.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountEntityMapper {

   RoleEntity mapToEntity(Role role);

   Role mapFromEntity(RoleEntity roleEntity);

   AccountEntity mapToEntity(Account Account);

   Account mapFromEntity(AccountEntity AccountEntity);

   Set<Role> mapRFromEntityList(Set<RoleEntity> roles);

   Set<RoleEntity> mapRToEntityList(Set<Role> roles);
}