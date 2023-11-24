package pl.flywithbookedseats.passengeraccountservice.domain.passenger.service;

import org.springframework.data.domain.Pageable;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PagePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;

public interface PassengerAccountService {

    PagePassengerAccount retrieveAllPassengerAccountsFromDb(Pageable pageable);

    PassengerAccount retrievePassengerAccountById(Long id);

    PassengerAccount retrievePassengerAccountByEmail(String email);

    public PassengerAccount createNewPassengerAccount(PassengerAccount passengerAccount);

    PassengerAccount updatePassengerAccountById(Long id, PassengerAccount passengerAccount);

    PassengerAccount updatePassengerAccountByEmail(String email, PassengerAccount passengerAccount);

    void deleteAllPassengerAccounts();

    void deletePassengerAccountById(Long id);

    void deletePassengerAccountByEmail(String email);

    PassengerAccount getPassengerDataFromBookingSystem(String email);

}
