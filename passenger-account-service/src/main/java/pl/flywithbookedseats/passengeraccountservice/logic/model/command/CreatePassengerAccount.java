<<<<<<<< HEAD:passenger-account-service/src/main/java/pl/flywithbookedseats/passengeraccountservice/api/passenger/CreatePassengerCommand.java
package pl.flywithbookedseats.passengeraccountservice.api.passenger;
========
package pl.flywithbookedseats.passengeraccountservice.logic.model.command;
>>>>>>>> 4cae5f3 (Caused by: java.lang.ClassNotFoundException:):passenger-account-service/src/main/java/pl/flywithbookedseats/passengeraccountservice/logic/model/command/CreatePassengerAccount.java

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

import static pl.flywithbookedseats.passengeraccountservice.logic.common.Consts.*;

public record CreatePassengerCommand(

        @NotBlank(message = NOT_BLANK_MESSAGE)
        @NotNull(message = NOT_NULL_MESSAGE)
        @Size(min = 2, message = NAME_MIN_FIELD_SIZE_MSG)
        String name,
        @NotBlank(message = NOT_BLANK_MESSAGE)
        @NotNull(message = NOT_NULL_MESSAGE)
        @Size(min = 2, message = SURNAME_MIN_FIELD_SIZE_MSG)
        String surname,
        @NotBlank(message = NOT_BLANK_MESSAGE)
        @NotNull(message = NOT_NULL_MESSAGE)
        @Size(min = 7, message = EMAIL_MIN_FIELD_SIZE_MSG)
        String email,
        @Past(message = PAST_MESSAGE)
        @NotNull(message = NOT_NULL_MESSAGE)
        LocalDate birthDate,
        @NotNull(message = NOT_NULL_MESSAGE)
        boolean disability,
        List<Long> reservationIdList,
        @NotBlank(message = NOT_BLANK_MESSAGE)
        @NotNull(message = NOT_NULL_MESSAGE)
        @Size(min = 2, message = NATIONALITY_MIN_FIELD_SIZE_MSG)
        String nationality,
        @NotBlank(message = NOT_BLANK_MESSAGE)
        @NotNull(message = NOT_NULL_MESSAGE)
        @Size(min = 4, message = GENDER_MIN_FIELD_SIZE_MSG)
        String gender
) {}
