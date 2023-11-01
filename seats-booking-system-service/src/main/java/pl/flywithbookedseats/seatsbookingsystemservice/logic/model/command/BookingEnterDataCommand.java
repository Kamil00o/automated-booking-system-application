package pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command;

import org.springframework.stereotype.Component;

@Component
public record BookingEnterDataCommand(
        String name,
        String surname,
        boolean disability,
        String passengerEmail,
        String flightNumber
) {}
