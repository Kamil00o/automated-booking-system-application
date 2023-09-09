package pl.flywithbookedseats.passengeraccountservice.model.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PassengerAccountDto(
        String name,
        String surname,
        String email,
        LocalDate birthDate,
        String healthState,
        String lifeStage
) {}
