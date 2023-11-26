package pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerDto;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.Passenger;

@Mapper(componentModel = "spring")
public interface PassengerDtoMapper {

    PassengerDto toDto(Passenger domain);

    Passenger toDomain(PassengerDto dto);
}
