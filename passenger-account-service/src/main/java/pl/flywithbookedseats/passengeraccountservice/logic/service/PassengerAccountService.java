package pl.flywithbookedseats.passengeraccountservice.logic.service;

import org.springframework.http.ResponseEntity;
import pl.flywithbookedseats.passengeraccountservice.logic.model.command.CreatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.logic.model.command.UpdatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.logic.model.domain.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.logic.model.dto.PassengerAccountDto;

import java.util.List;

public interface PassengerAccountService {

    List<PassengerAccount> retrieveAllPassengerAccountsFromDb();

    PassengerAccountDto retrievePassengerAccountById(Long id);

    PassengerAccountDto retrievePassengerAccountByEmail(String email);

    public ResponseEntity<Object> createNewPassengerAccount(CreatePassengerAccount createPassengerAccount);

    PassengerAccountDto updatePassengerAccountById(long id, UpdatePassengerAccount updatePassengerAccount);

    PassengerAccountDto updatePassengerAccountByEmail(UpdatePassengerAccount updatePassengerAccount, String email);

    void deleteAllPassengerAccounts();

    void deletePassengerAccountById(Long id);

    void deletePassengerAccountByEmail(String email);

}
