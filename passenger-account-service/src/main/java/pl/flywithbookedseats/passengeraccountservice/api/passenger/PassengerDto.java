package pl.flywithbookedseats.passengeraccountservice.api.passenger;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record PassengerDto(
        Long passengerServiceId,
        String name,
        String surname,
        String email,
        LocalDate birthDate,
        boolean disability,
        List<Long> reservationsIdList
) {}
