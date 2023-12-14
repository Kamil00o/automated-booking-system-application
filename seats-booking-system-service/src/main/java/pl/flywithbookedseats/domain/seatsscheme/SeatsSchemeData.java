package pl.flywithbookedseats.domain.seatsscheme;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SeatsSchemeData {

    private String planeModelName;
    private List<String> seatClassTypeList;
    private List<Integer> amountOfSeatsInARowPerSeatClassTypeList;
    private List<Integer> amountOfRowsPerSeatClassTypeList;
    private List<Integer> numbersOfExcludedSeatsList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeatsSchemeData that)) return false;
        return Objects.equals(planeModelName, that.planeModelName) && Objects.equals(seatClassTypeList, that.seatClassTypeList) && Objects.equals(amountOfSeatsInARowPerSeatClassTypeList, that.amountOfSeatsInARowPerSeatClassTypeList) && Objects.equals(amountOfRowsPerSeatClassTypeList, that.amountOfRowsPerSeatClassTypeList) && Objects.equals(numbersOfExcludedSeatsList, that.numbersOfExcludedSeatsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planeModelName, seatClassTypeList, amountOfSeatsInARowPerSeatClassTypeList, amountOfRowsPerSeatClassTypeList, numbersOfExcludedSeatsList);
    }
}
