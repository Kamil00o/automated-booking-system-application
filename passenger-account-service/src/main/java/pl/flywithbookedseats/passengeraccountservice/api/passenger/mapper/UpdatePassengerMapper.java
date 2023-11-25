package pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.UpdatePassengerCommand;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.Passenger;

@Mapper(componentModel = "spring")
public interface UpdatePassengerMapper {

    UpdatePassengerCommand toCommand(Passenger domain);

    Passenger toDomain(UpdatePassengerCommand updateCommand);
}
