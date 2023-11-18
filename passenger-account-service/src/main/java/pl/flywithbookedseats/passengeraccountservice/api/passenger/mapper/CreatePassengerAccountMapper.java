package pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.CreatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;

import java.util.function.Function;

@Component
public class CreatePassengerAccountMapper implements Function<CreatePassengerAccountCommand, PassengerAccount> {
    @Override
    public PassengerAccount apply(CreatePassengerAccountCommand createPassengerAccountCommand) {
        return PassengerAccount.builder()
                .name(createPassengerAccountCommand.name())
                .surname(createPassengerAccountCommand.surname())
                .email(createPassengerAccountCommand.email())
                .birthDate(createPassengerAccountCommand.birthDate())
                .disability(createPassengerAccountCommand.disability())
                .reservationIdList(createPassengerAccountCommand.reservationIdList())
                .nationality(createPassengerAccountCommand.nationality())
                .gender(createPassengerAccountCommand.gender())
                .build();
    }
}
