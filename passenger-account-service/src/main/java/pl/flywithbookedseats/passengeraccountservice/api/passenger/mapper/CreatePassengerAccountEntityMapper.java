package pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.CreatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.entity.PassengerAccountEntity;

import java.util.function.Function;

@Component
public class CreatePassengerAccountEntityMapper implements Function<CreatePassengerAccountCommand, PassengerAccountEntity> {
    @Override
    public PassengerAccountEntity apply(CreatePassengerAccountCommand createPassengerAccountCommand) {
        return PassengerAccountEntity.builder()
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
