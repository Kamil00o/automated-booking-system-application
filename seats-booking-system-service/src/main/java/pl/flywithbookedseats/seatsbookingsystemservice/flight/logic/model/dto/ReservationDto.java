package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public record ReservationDto(
        Long id,
        String flightNumber,
        String seatNumber,
        String seatTypeClass,
        String passengerEmail
)
{}
