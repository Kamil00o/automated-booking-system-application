package pl.flywithbookedseats.api.passeger;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.api.passeger.CreatePassengerCommand;
import pl.flywithbookedseats.external.storage.passenger.Passenger;

import java.util.function.Function;

@Component
public class CreatePassengerMapper implements Function<CreatePassengerCommand, Passenger> {
    @Override
    public Passenger apply(CreatePassengerCommand createPassengerCommand) {
        return Passenger.builder()
                .passengerServiceId(createPassengerCommand.passengerServiceId())
                .email(createPassengerCommand.email())
                .name(createPassengerCommand.name())
                .surname(createPassengerCommand.surname())
                .birthDate(createPassengerCommand.birthDate())
                .disability(createPassengerCommand.disability())
                .build();
    }
}
