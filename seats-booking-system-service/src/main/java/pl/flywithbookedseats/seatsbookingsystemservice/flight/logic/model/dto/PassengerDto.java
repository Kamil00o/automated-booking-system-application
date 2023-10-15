package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Setter
@Getter
public record PassengerDto(
        String email,
        String name,
        String surname,
        LocalDate birthDate,
        boolean disability,
        List<Long> reservationsIdList
) {}
