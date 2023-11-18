package pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.UpdatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;

import java.util.function.Function;

@Component
public class UpdatePassengerAccountMapper implements Function<UpdatePassengerAccountCommand, PassengerAccount> {
    @Override
    public PassengerAccount apply(UpdatePassengerAccountCommand updatePassengerAccountCommand) {
        return PassengerAccount.builder()
                .name(updatePassengerAccountCommand.name())
                .surname(updatePassengerAccountCommand.surname())
                .email(updatePassengerAccountCommand.email())
                .birthDate(updatePassengerAccountCommand.birthDate())
                .disability(updatePassengerAccountCommand.disability())
                .nationality(updatePassengerAccountCommand.nationality())
                .gender(updatePassengerAccountCommand.gender())
                .build();
    }
}
