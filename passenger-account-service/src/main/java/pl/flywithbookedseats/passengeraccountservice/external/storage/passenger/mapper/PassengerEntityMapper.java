package pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.mapper;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.Passenger;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.entity.PassengerEntity;

@Mapper(componentModel = "spring")
public interface PassengerEntityMapper {

    PassengerEntity toEntity(Passenger domain);

    Passenger toDomain(PassengerEntity entity);
}
