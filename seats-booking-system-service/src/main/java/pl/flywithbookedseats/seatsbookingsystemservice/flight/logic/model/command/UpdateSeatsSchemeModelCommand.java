package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.command;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

import static pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.common.Consts.NOT_NULL_MESSAGE;

@Builder
public record UpdateSeatsSchemeModelCommand(
        @NotNull(message = NOT_NULL_MESSAGE)
        String planeModelName
) {}
