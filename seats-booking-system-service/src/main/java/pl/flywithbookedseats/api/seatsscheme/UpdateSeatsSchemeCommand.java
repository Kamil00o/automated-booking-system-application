package pl.flywithbookedseats.api.seatsscheme;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import pl.flywithbookedseats.common.Consts;

@Builder
public record UpdateSeatsSchemeCommand(
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        String planeModelName
) {}
