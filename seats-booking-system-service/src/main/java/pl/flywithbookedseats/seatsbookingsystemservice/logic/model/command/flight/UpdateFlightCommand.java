package pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.flight;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

import java.util.Map;

import static pl.flywithbookedseats.seatsbookingsystemservice.logic.common.Consts.NOT_NULL_MESSAGE;

@SchemaMapping
public record UpdateFlightCommand(
        Long flightServiceId,
        @NotNull(message = NOT_NULL_MESSAGE)
        @Size(min = 4, message = "The flightName field should have at least 4 signs")
        String flightName,
        @NotNull(message = NOT_NULL_MESSAGE)
        @Size(min = 4, message = "The planeTypeName field should have at least 4 signs")
        String planeTypeName,
        Map<String, String> bookedSeatsInPlaneMap
) {}
