package pl.flywithbookedseats.external.message.passenger;

import java.time.LocalDate;
import java.util.List;

public record KafkaPassengerDto(

        Long passengerServiceId,
        String name,
        String surname,
        String email,
        LocalDate birthDate,
        boolean disability,
        List<Long> reservationsIdList
) {}
