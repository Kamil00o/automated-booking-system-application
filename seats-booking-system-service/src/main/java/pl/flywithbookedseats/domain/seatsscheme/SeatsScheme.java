package pl.flywithbookedseats.domain.seatsscheme;


import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SeatsScheme {

    private Long id;
    private String planeModelName;
    private List<String> seatClassTypeList;
    private Map<String, String> seatsSchemeMap;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeatsScheme that)) return false;
        return Objects.equals(id, that.id)
                && Objects.equals(planeModelName, that.planeModelName)
                && Objects.equals(seatClassTypeList, that.seatClassTypeList)
                && Objects.equals(seatsSchemeMap, that.seatsSchemeMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, planeModelName, seatClassTypeList, seatsSchemeMap);
    }
}
