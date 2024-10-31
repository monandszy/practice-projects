package code.component.manageAccount.domain.mapper;

import code.component.manageAccount.domain.Account;
import code.component.manageAccount.domain.AccountDTO;
import code.component.manageAccount.domain.Role;
import code.component.manageAccount.domain.RoleDTO;
import code.configuration.Generated;
import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Objects;
import java.util.Set;
@AnnotateWith(Generated.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountDTOMapper {

   Role mapFromDTO(RoleDTO roleDTO);

   @Mapping(target = "roles", source = "roles", qualifiedByName = "roleMapping")
   AccountDTO mapToDTO(Account account);

   @Mapping(target = "roles", ignore = true)
   Account mapFromDTO(AccountDTO accountDTO);

   List<AccountDTO> mapAToDTOList(List<Account> accountPage);


      @Named("roleMapping")
      default String roleMapping(Set<Role> roles) {
         if (Objects.isNull(roles)) return null;
         return roles.stream().map(e -> e.getRoleName().name())
             .reduce((l, r) -> l + "," + r).orElse("none");
      }
}