package code.component.api.ipAddressApi;

import code.component.manageRestaurant.manageDelivery.domain.Address;
import code.configuration.Generated;
import code.openApi.model.InlineResponse200;
import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@AnnotateWith(Generated.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressApiMapper {

   Address mapToAddress(InlineResponse200 inlineResponse200);

}