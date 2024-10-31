package code.component.manageRestaurant.manageDelivery.domain;

import code.configuration.Generated;
import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@AnnotateWith(Generated.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressDTOMapper {
   public Address mapFromDTO(AddressDTO address);

}