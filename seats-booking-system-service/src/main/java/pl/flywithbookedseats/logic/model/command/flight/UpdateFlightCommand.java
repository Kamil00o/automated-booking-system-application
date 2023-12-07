package pl.flywithbookedseats.logic.model.command.flight;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import pl.flywithbookedseats.logic.common.Consts;

import java.util.Map;

@SchemaMapping
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