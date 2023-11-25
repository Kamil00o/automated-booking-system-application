package pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.CreatePassengerCommand;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.Passenger;

@Mapper(componentModel = "spring")
public interface CreatePassengerMapper {

    CreatePassengerCommand toCommand(Passenger domain);

    Passenger toDomain(CreatePassengerCommand createCommand);

}
