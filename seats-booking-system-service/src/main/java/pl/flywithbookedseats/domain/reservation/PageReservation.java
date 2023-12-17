package pl.flywithbookedseats.domain.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.flywithbookedseats.domain.flight.Flight;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageReservation {
    private List<Reservation> reservations;
    private Integer currentPage;
    private Integer totalPages;
    private Long totalElements;
}
