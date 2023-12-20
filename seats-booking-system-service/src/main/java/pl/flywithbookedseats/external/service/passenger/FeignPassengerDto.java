package pl.flywithbookedseats.external.service.passenger;

import java.time.LocalDate;
import java.util.List;

public record FeignPassengerDto(

        Long passengerServiceId,
        String name,
        String surname,
        String email,
        LocalDate birthDate,
        boolean disability,
        List<Long> reservationsIdList
) {}
