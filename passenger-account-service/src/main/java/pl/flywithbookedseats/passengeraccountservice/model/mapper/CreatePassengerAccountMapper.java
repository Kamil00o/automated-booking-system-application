package pl.flywithbookedseats.passengeraccountservice.model.mapper;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.passengeraccountservice.model.command.CreatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.model.domain.PassengerAccount;

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
