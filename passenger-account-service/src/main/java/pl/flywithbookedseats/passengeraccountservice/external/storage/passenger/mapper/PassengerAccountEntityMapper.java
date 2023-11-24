package pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.mapper;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.entity.PassengerAccountEntity;

@Mapper(componentModel = "spring")
public interface PassengerAccountEntityMapper {

    PassengerAccountEntity toEntity(PassengerAccount domain);

    PassengerAccount toDomain(PassengerAccountEntity entity);
}
