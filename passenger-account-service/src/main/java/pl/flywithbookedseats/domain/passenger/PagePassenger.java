package pl.flywithbookedseats.passengeraccountservice.domain.passenger;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class PagePassenger implements Serializable {

    List<Passenger> passengers;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}
