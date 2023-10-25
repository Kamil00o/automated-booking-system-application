package pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.seatsschememodel;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

import static pl.flywithbookedseats.seatsbookingsystemservice.logic.common.Consts.NOT_NULL_MESSAGE;

@Builder
public record CreateSeatsSchemeModelCommand(
        @NotNull(message = NOT_NULL_MESSAGE)
        String planeModelName,
        @NotNull(message = NOT_NULL_MESSAGE)
        List<String> seatClassTypeList,
        @NotNull(message = NOT_NULL_MESSAGE)
        List<Integer> amountOfSeatsInARowPerSeatClassTypeList,
        @NotNull(message = NOT_NULL_MESSAGE)
        List<Integer> amountOfRowsPerSeatClassTypeList,
        @NotNull(message = NOT_NULL_MESSAGE)
        List<Integer> numbersOfExcludedSeatsList
) {}