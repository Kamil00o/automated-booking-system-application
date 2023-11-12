package pl.flywithbookedseats.passengeraccountservice.service;

import org.springframework.http.ResponseEntity;
import pl.flywithbookedseats.passengeraccountservice.model.command.CreatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.command.UpdatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.domain.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.dto.PassengerAccountDto;

import java.util.List;

public interface PassengerAccountService {

    List<PassengerAccount> retrieveAllPassengerAccountsFromDb();
    
    PassengerAccountDto retrievePassengerAccountById(Long id);

    PassengerAccountDto retrievePassengerAccountByEmail(String email);

    public ResponseEntity<Object> createNewPassengerAccount(CreatePassengerAccount createPassengerAccount);

    void editPassengerAccount(long id, UpdatePassengerAccount updatePassengerAccount);

}
