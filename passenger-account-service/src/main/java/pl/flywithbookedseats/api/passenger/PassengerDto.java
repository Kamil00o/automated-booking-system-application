<<<<<<<< HEAD:passenger-account-service/src/main/java/pl/flywithbookedseats/passengeraccountservice/api/passenger/PassengerDto.java
package pl.flywithbookedseats.passengeraccountservice.api.passenger;
========
package pl.flywithbookedseats.passengeraccountservice.logic.model.dto;
>>>>>>>> 4cae5f3 (Caused by: java.lang.ClassNotFoundException:):passenger-account-service/src/main/java/pl/flywithbookedseats/passengeraccountservice/logic/model/dto/PassengerAccountDto.java

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
