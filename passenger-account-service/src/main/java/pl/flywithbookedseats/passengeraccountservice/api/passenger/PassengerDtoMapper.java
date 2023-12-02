package pl.flywithbookedseats.passengeraccountservice.api.passenger;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.Passenger;

@Mapper(componentModel = "spring")
public interface PassengerDtoMapper {

    PassengerDto toDto(Passenger domain);

    Passenger toDomain(PassengerDto dto);
}
