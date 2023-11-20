package pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.CreatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;

@Mapper(componentModel = "spring")
public interface CreatePassengerAccountMapper {

    CreatePassengerAccountCommand toCommand(PassengerAccount domain);

    PassengerAccount toDomain(CreatePassengerAccountCommand createCommand);

}
