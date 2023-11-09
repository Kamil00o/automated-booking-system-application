package pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.reservation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Passenger;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.common.Consts;

@Builder
public record CreateReservationCommand(
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        @Size(min = 4, message = "The flightName field should have at least 4 signs")
        String flightNumber,
        @Size(min = 3, message = "The seatNumber field should consists of 4 signs", max = 3)
        String seatNumber,
        @Size(min = 3, message = "The seatTypeClass field should consists of 20 signs", max = 20)
        String seatTypeClass,
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        @Size(min = 9, message = "The passengerEmail field should have at least 9 signs")
        String passengerEmail
)
{}