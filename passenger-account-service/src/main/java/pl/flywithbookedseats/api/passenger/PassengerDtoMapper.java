package pl.flywithbookedseats.api.passenger;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.flywithbookedseats.domain.passenger.Passenger;

@Mapper(componentModel = "spring")
public interface PassengerDtoMapper {

    @Mapping(target = "passengerServiceId", source = "domain.id")
    PassengerDto toDto(Passenger domain);

    Passenger toDomain(PassengerDto dto);

}
