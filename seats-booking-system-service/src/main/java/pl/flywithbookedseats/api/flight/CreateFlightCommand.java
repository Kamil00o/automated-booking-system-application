package pl.flywithbookedseats.api.flight;

import jakarta.validation.constraints.*;
import lombok.Builder;
import pl.flywithbookedseats.common.Consts;

import java.util.Map;

@Builder
public record CreateFlightCommand(
        Long flightServiceId,
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        @Size(min = 4, message = "The flightName field should have at least 4 signs")
        String flightName,
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        @Size(min = 4, message = "The planeTypeName field should have at least 4 signs")
        String planeTypeName,
        Map<String, Long> bookedSeatsInPlaneList
) {}
