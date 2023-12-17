package pl.flywithbookedseats.api.reservation;

import pl.flywithbookedseats.domain.reservation.Reservation;

import java.util.List;

public record PageReservationDto(

        List<Reservation> reservations,
        Integer currentPage,
        Integer totalPages,
        Long totalElements
) {}
