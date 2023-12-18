package pl.flywithbookedseats.api.reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pl.flywithbookedseats.common.Consts;
import pl.flywithbookedseats.external.storage.passenger.Passenger;

public record UpdateReservationCommand(
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        @Size(min = 4, message = "The flightName field should have at least 4 signs")
        String flightNumber,
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        @Size(min = 3, message = "The seatNumber field should consists of 4 signs", max = 4)
        String seatNumber,
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        @Size(min = 3, message = "The seatTypeClass field should consists of 20 signs", max = 20)
        String seatTypeClass,
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        @Size(min = 9, message = "The passengerEmail field should have at least 9 signs")
        String passengerEmail,
        @ManyToOne(fetch = FetchType.LAZY)
        @JsonIgnore
        Passenger passenger
)
{}
