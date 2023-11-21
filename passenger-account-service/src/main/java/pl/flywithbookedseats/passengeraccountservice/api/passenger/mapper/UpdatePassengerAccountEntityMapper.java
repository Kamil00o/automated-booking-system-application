package pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.UpdatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.entity.PassengerAccountEntity;

import java.util.function.Function;

@Component
public class UpdatePassengerAccountEntityMapper implements Function<UpdatePassengerAccountCommand, PassengerAccountEntity> {
    @Override
    public PassengerAccountEntity apply(UpdatePassengerAccountCommand updatePassengerAccountCommand) {
        return PassengerAccountEntity.builder()
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
