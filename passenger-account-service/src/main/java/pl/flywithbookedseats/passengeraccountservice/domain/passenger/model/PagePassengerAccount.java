package pl.flywithbookedseats.passengeraccountservice.domain.passenger.model;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class PagePassengerAccount implements Serializable {

    List<PassengerAccount> passengerAccounts;
    Long currentPage;
    Long totalPages;
    Long totalElements;
}
