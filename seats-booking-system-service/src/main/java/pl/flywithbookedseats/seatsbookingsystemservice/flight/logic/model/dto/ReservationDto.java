package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.dto;

import lombok.Builder;

@Builder
public record ReservationDto(
        Long id,
        String flightNumber,
        String seatNumber,
        String seatTypeClass,
        String passengerEmail
)
{}
