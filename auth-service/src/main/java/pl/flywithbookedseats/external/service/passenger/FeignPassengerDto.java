package pl.flywithbookedseats.external.service.passenger;

import pl.flywithbookedseats.domain.user.UserRole;

import java.time.LocalDate;
import java.util.List;

public record FeignPassengerDto(

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
