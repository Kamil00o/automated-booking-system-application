package pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command;

import lombok.Builder;
import org.springframework.stereotype.Component;

@Builder
public record BookingEnterDataCommand(
        String name,
        String surname,
        boolean disability,
        String passengerEmail,
        String flightNumber,
        String seatClassType
) {}
