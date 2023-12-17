package pl.flywithbookedseats.api.reservation;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.external.storage.reservation.Reservation;

import java.util.function.Function;

@Component
public class ReservationDtoMapper implements Function<Reservation, ReservationDto> {
    @Override
    public ReservationDto apply(Reservation reservation) {
        return ReservationDto.builder()
                .id(reservation.getId())
                .flightNumber(reservation.getFlightNumber())
                .seatNumber(reservation.getSeatNumber())
                .seatTypeClass(reservation.getSeatTypeClass())
                .passengerEmail(reservation.getPassengerEmail())
                .build();
    }
}
