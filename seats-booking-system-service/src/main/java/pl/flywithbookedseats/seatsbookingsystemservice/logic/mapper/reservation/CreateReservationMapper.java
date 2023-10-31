package pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.reservation;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.reservation.CreateReservationCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Reservation;

import java.util.function.Function;

@Component
public class CreateReservationMapper implements Function<CreateReservationCommand, Reservation> {
    @Override
    public Reservation apply(CreateReservationCommand createReservationCommand) {
        return Reservation.builder()
                .flightNumber(createReservationCommand.flightNumber())
                .seatNumber(createReservationCommand.seatNumber())
                .seatTypeClass(createReservationCommand.seatTypeClass())
                .passengerEmail(createReservationCommand.passengerEmail())
                .build();
    }
}
