package pl.flywithbookedseats.api.reservation;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.external.storage.reservation.ReservationEntity;

import java.util.function.Function;

@Component
public class ReservationDtoMapper1 implements Function<ReservationEntity, ReservationDto> {
    @Override
    public ReservationDto apply(ReservationEntity reservationEntity) {
        return ReservationDto.builder()
                .id(reservationEntity.getId())
                .flightNumber(reservationEntity.getFlightNumber())
                .seatNumber(reservationEntity.getSeatNumber())
                .seatTypeClass(reservationEntity.getSeatTypeClass())
                .passengerEmail(reservationEntity.getPassengerEmail())
                .build();
    }
}
