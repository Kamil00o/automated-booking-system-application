package pl.flywithbookedseats.logic.model.command.seatsschememodel;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import pl.flywithbookedseats.logic.common.Consts;

import java.util.List;

@Builder
public record CreateSeatsSchemeModelCommand(
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        String planeModelName,
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        List<String> seatClassTypeList,
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        List<Integer> amountOfSeatsInARowPerSeatClassTypeList,
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        List<Integer> amountOfRowsPerSeatClassTypeList,
        @NotNull(message = Consts.NOT_NULL_MESSAGE)
        List<Integer> numbersOfExcludedSeatsList
) {}