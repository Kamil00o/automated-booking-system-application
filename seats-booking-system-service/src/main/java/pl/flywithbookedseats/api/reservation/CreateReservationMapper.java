package pl.flywithbookedseats.api.reservation;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.external.storage.reservation.ReservationEntity;

import java.util.function.Function;

@Component
public class CreateReservationMapper implements Function<CreateReservationCommand, ReservationEntity> {
    @Override
    public ReservationEntity apply(CreateReservationCommand createReservationCommand) {
        return ReservationEntity.builder()
                .flightNumber(createReservationCommand.flightNumber())
                .seatNumber(createReservationCommand.seatNumber())
                .seatTypeClass(createReservationCommand.seatTypeClass())
                .passengerEmail(createReservationCommand.passengerEmail())
                .build();
    }
}
