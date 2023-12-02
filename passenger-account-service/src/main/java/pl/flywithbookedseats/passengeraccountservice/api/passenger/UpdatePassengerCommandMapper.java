package pl.flywithbookedseats.passengeraccountservice.api.passenger;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.Passenger;

@Mapper(componentModel = "spring")
public interface UpdatePassengerCommandMapper {

    Passenger toDomain(UpdatePassengerCommand updateCommand);
}
