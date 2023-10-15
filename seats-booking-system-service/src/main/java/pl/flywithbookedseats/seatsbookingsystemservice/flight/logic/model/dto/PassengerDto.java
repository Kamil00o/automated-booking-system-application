package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.dto;

import java.time.LocalDate;
import java.util.List;

public record PassengerDto(
        String email,
        String name,
        String surname,
        LocalDate birthDate,
        boolean disability,
        List<Long> reservationsIdList
) {}
