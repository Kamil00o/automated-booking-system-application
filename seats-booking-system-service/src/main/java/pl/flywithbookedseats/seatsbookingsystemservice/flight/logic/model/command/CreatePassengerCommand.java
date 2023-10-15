package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import static pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.common.Consts.NOT_NULL_MESSAGE;

@Getter
@Setter
@Builder
public record CreatePassengerCommand(
        @NotNull(message = NOT_NULL_MESSAGE)
        @Size(min = 9, message = "The email field should have at least 9 signs")
                String email,
        @NotNull(message = NOT_NULL_MESSAGE)
        @Size(min = 2, message = "The name should have at least 2 signs")
        String name,
        @NotNull(message = NOT_NULL_MESSAGE)
        @Size(min = 2, message = "The name should have at least 2 signs")
        String surname,
        @NotNull(message = NOT_NULL_MESSAGE)
        @Past
        LocalDate birthDate,
        @NotNull(message = NOT_NULL_MESSAGE)
        boolean disability,
        List<Long> reservationsIdList
)
{}
