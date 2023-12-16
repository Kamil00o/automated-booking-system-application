package pl.flywithbookedseats.domain.flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Flight {

    private Long id;
    private Long flightServiceId;
    private String flightName;
    private String planeTypeName;
    private Map<String, Long> bookedSeatsInPlaneMap;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight flight)) return false;
        return Objects.equals(id, flight.id) && Objects.equals(flightServiceId, flight.flightServiceId) && Objects.equals(flightName, flight.flightName) && Objects.equals(planeTypeName, flight.planeTypeName) && Objects.equals(bookedSeatsInPlaneMap, flight.bookedSeatsInPlaneMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flightServiceId, flightName, planeTypeName, bookedSeatsInPlaneMap);
    }
}
