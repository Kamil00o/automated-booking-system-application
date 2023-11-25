package pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.Passenger;

@Mapper(componentModel = "spring")
public interface PassengerDtoMapper {

    PassengerAccountDto toDto(Passenger domain);

    Passenger toDomain(PassengerAccountDto dto);
}
