package pl.flywithbookedseats.api.flight;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pl.flywithbookedseats.common.Consts;

import java.util.Map;

public record UpdateFlightCommand(
        Long flightServiceId,
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        @Size(min = 4, message = "The flightName field should have at least 4 signs")
        String flightName,
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        @Size(min = 4, message = "The planeTypeName field should have at least 4 signs")
        String planeTypeName,
        Map<String, Long> bookedSeatsInPlaneMap
) {}
