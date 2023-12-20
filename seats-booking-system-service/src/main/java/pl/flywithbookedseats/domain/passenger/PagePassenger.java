package pl.flywithbookedseats.domain.passenger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PagePassenger {

    private List<Passenger> passengers;
    private Integer currentPage;
    private Integer totalPages;
    private Long totalElements;
}
