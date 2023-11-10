package pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.passenger;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.common.Consts;

import java.time.LocalDate;
import java.util.List;

@Builder
public record CreatePassengerCommand(
        Long passengerServiceId,
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        @Size(min = 9, message = "The email field should have at least 9 signs")
                String email,
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        @Size(min = 2, message = "The name should have at least 2 signs")
        String name,
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        @Size(min = 2, message = "The name should have at least 2 signs")
        String surname,
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        @Past
        LocalDate birthDate,
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        boolean disability,
        List<Long> reservationsIdList
)
{}
