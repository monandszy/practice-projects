package code.component.manageRestaurant.manageDelivery;

import code.component.manageRestaurant.manageDelivery.domain.Address;
import code.component.manageRestaurant.manageDelivery.domain.AddressEntity;
import code.component.manageRestaurant.manageDelivery.domain.AddressEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class AddressRepo implements AddressDAO {

   private AddressJpaRepo addressJpaRepo;
   private AddressEntityMapper entityMapper;

   @Override
   public Address addOrFindByIp(Address address) {
      Optional<AddressEntity> byIpAddress = addressJpaRepo
          .findByIpAddress(address.getIpAddress());
      if (byIpAddress.isPresent()) {
         return entityMapper.mapFromEntity(byIpAddress.get());
      } else {
         return entityMapper.mapFromEntity(addressJpaRepo
             .save(entityMapper.mapToEntity(address)));
      }
   }

   @Override
   public Optional<Address> getByIp(String ip) {
      return addressJpaRepo.findByIpAddress(ip)
          .map(e -> entityMapper.mapFromEntity(e));
   }

   @Override
   public void add(Address address) {
      addressJpaRepo.save(entityMapper.mapToEntity(address));
   }

}