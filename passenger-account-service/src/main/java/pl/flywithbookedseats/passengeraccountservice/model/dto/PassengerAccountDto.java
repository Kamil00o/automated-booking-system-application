package pl.flywithbookedseats.passengeraccountservice.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
public record PassengerAccountDto(

        String name,
        String surname,
        String email,
        LocalDate birthDate,
        boolean disability,
        List<String> reservationId
) {}
