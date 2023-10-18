package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.command;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.common.Consts.NOT_NULL_MESSAGE;

@Builder
@Getter
@Setter
public record CreateSeatsSchemeModelCommand(
        @NotNull(message = NOT_NULL_MESSAGE)
        String planeModel,
        @NotNull(message = NOT_NULL_MESSAGE)
        List<String> seatClassType,
        @NotNull(message = NOT_NULL_MESSAGE)
        List<Integer> amountOfSeatsPerSeatClassType,
        @NotNull(message = NOT_NULL_MESSAGE)
        List<Integer> amountOfRowsPerSeatClassType
) {}
