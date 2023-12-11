package pl.flywithbookedseats.api.seatsscheme;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import pl.flywithbookedseats.common.Consts;

import java.util.List;

@Builder
public record CreateSeatsSchemeCommand(
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