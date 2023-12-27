package pl.flywithbookedseats.api.passenger;


import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record UserDto(
        Long passengerServiceId,
        String name,
        String surname,
        String email,
        String password,
        String role,
        LocalDate birthDate,
        boolean disability,
        List<Long> reservationsIdList
) {}
