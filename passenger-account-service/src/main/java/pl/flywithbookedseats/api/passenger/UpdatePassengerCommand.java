package pl.flywithbookedseats.api.passenger;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

import static pl.flywithbookedseats.common.Consts.*;

public record UpdatePassengerCommand(

        @NotNull(message = NOT_NULL_MESSAGE)
        @NotBlank(message = NOT_BLANK_MESSAGE)
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
        String password,
        @Past(message = PAST_MESSAGE)
        @NotNull(message = NOT_NULL_MESSAGE)
        LocalDate birthDate,
        boolean disability,
        List<Long> reservationsIdList,
        @NotBlank(message = NOT_BLANK_MESSAGE)
        @NotNull(message = NOT_NULL_MESSAGE)
        @Size(min = 2, message = NATIONALITY_MIN_FIELD_SIZE_MSG)
        String nationality,
        @NotBlank(message = NOT_BLANK_MESSAGE)
        @NotNull(message = NOT_NULL_MESSAGE)
        @Size(min = 4, message = GENDER_MIN_FIELD_SIZE_MSG)
        String gender
) {}
