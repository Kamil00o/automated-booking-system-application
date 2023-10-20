package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.command;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

import static pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.common.Consts.NOT_NULL_MESSAGE;

@Builder
public record CreateSeatsSchemeModelCommand(
        @NotNull(message = NOT_NULL_MESSAGE)
        String planeModel,
        @NotNull(message = NOT_NULL_MESSAGE)
        List<String> seatClassTypeList,
        @NotNull(message = NOT_NULL_MESSAGE)
        List<Integer> amountOfSeatsInARowPerSeatClassTypeList,
        @NotNull(message = NOT_NULL_MESSAGE)
        List<Integer> amountOfRowsPerSeatClassTypeList
) {}
