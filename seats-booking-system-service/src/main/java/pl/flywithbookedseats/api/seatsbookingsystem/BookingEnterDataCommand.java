package pl.flywithbookedseats.api.seatsbookingsystem;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BookingEnterDataCommand(
        String name,
        String surname,
        boolean disability,
        String passengerEmail,
        LocalDate passengerBirthDate,
        String flightName,
        String seatClassType
) {}
