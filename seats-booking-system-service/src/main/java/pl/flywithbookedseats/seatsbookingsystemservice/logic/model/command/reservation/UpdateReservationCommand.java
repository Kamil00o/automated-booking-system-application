package pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Passenger;

import static pl.flywithbookedseats.seatsbookingsystemservice.logic.common.Consts.NOT_NULL_MESSAGE;

public record UpdateReservationCommand(
        @NotNull(message = NOT_NULL_MESSAGE)
        @Size(min = 4, message = "The flightName field should have at least 4 signs")
        String flightNumber,
        @NotNull(message = NOT_NULL_MESSAGE)
        @Size(min = 3, message = "The seatNumber field should consists of 4 signs", max = 4)
        String seatNumber,
        @NotNull(message = NOT_NULL_MESSAGE)
        @Size(min = 3, message = "The seatTypeClass field should consists of 20 signs", max = 20)
        String seatTypeClass,
        @NotNull(message = NOT_NULL_MESSAGE)
        @Size(min = 9, message = "The passengerEmail field should have at least 9 signs")
        String passengerEmail,
        @ManyToOne(fetch = FetchType.LAZY)
        @JsonIgnore
        Passenger passenger
)
{}
