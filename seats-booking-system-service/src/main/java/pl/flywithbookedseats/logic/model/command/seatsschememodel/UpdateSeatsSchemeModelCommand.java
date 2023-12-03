package pl.flywithbookedseats.logic.model.command.seatsschememodel;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import pl.flywithbookedseats.logic.common.Consts;

@Builder
public record UpdateSeatsSchemeModelCommand(
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        String planeModelName
) {}
