package pl.flywithbookedseats.passengeraccountservice.service;

import org.springframework.http.ResponseEntity;
import pl.flywithbookedseats.passengeraccountservice.model.command.UpdatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.domain.PassengerAccount;

import java.util.List;
import java.util.Optional;

public interface PassengerAccountService {

    List<PassengerAccount> retrieveAllPassengerAccountsFromDb();
    Optional<PassengerAccount> getPassengerAccountById(Long id);
    ResponseEntity<Object> createNewPassengerAccount(PassengerAccount passengerAccount);
    void editPassengerAccount(long id, UpdatePassengerAccount updatePassengerAccount);
}
