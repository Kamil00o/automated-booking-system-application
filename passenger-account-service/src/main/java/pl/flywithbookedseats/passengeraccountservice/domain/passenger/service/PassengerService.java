package pl.flywithbookedseats.passengeraccountservice.domain.passenger.service;

import org.springframework.data.domain.Pageable;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PagePassenger;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.Passenger;

public interface PassengerService {

    PagePassenger retrieveAllPassengerAccountsFromDb(Pageable pageable);

    Passenger retrievePassengerAccountById(Long id);

    Passenger retrievePassengerAccountByEmail(String email);

    public Passenger createNewPassengerAccount(Passenger passenger);

    Passenger updatePassengerAccountById(Long id, Passenger passenger);

    Passenger updatePassengerAccountByEmail(String email, Passenger passenger);

    void deleteAllPassengerAccounts();

    void deletePassengerAccountById(Long id);

    void deletePassengerAccountByEmail(String email);

    Passenger getPassengerDataFromBookingSystem(String email);

}
