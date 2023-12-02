package pl.flywithbookedseats.passengeraccountservice.api.passenger;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.Passenger;

@Mapper(componentModel = "spring")
public interface CreatePassengerCommandMapper {

    Passenger toDomain(CreatePassengerCommand createCommand);

}
