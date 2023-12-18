package pl.flywithbookedseats.api.passeger;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.external.storage.passenger.PassengerEntity;

import java.util.function.Function;

@Component
public class CreatePassengerMapper implements Function<CreatePassengerCommand, PassengerEntity> {
    @Override
    public PassengerEntity apply(CreatePassengerCommand createPassengerCommand) {
        return PassengerEntity.builder()
                .passengerServiceId(createPassengerCommand.passengerServiceId())
                .email(createPassengerCommand.email())
                .name(createPassengerCommand.name())
                .surname(createPassengerCommand.surname())
                .birthDate(createPassengerCommand.birthDate())
                .disability(createPassengerCommand.disability())
                .build();
    }
}
