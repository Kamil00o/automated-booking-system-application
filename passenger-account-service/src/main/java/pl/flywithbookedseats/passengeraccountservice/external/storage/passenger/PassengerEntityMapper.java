package pl.flywithbookedseats.passengeraccountservice.external.storage.passenger;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.Passenger;

@Mapper(componentModel = "spring")
public interface PassengerEntityMapper {

    PassengerEntity toEntity(Passenger domain);

    Passenger toDomain(PassengerEntity entity);
}
