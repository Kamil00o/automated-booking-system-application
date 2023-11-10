package pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.passenger;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Passenger;

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
