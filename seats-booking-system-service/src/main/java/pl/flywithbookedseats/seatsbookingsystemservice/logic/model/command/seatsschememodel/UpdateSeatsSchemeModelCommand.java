package pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.seatsschememodel;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.common.Consts;

@Builder
public record UpdateSeatsSchemeModelCommand(
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        String planeModelName
) {}
