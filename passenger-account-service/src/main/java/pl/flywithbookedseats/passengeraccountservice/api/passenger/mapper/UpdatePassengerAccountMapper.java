package pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.UpdatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;

@Mapper(componentModel = "spring")
public interface UpdatePassengerAccountMapper {

    UpdatePassengerAccountCommand toCommand(PassengerAccount domain);

    PassengerAccount toDomain(UpdatePassengerAccountCommand updateCommand);
}
