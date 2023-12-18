package pl.flywithbookedseats.api.passeger;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record PassengerDto(
        Long passengerServiceId,
        String email,
        String name,
        String surname,
        LocalDate birthDate,
        boolean disability,
        List<Long> reservationsIdList
) {}
