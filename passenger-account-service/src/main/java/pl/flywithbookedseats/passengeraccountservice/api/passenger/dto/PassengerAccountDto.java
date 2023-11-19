package pl.flywithbookedseats.passengeraccountservice.api.passenger.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record PassengerAccountDto(
        Long passengerServiceId,
        String name,
        String surname,
        String email,
        LocalDate birthDate,
        boolean disability,
        List<Long> reservationsIdList
) {}