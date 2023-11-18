package pl.flywithbookedseats.passengeraccountservice.domain.passenger.service;

import org.springframework.http.ResponseEntity;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.CreatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.UpdatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerAccountDto;

import java.util.List;

public interface PassengerAccountService {

    List<PassengerAccount> retrieveAllPassengerAccountsFromDb();

    PassengerAccountDto retrievePassengerAccountById(Long id);

    PassengerAccountDto retrievePassengerAccountByEmail(String email);

    public ResponseEntity<Object> createNewPassengerAccount(CreatePassengerAccountCommand createPassengerAccountCommand);

    PassengerAccountDto updatePassengerAccountById(long id, UpdatePassengerAccount updatePassengerAccount);

    PassengerAccountDto updatePassengerAccountByEmail(UpdatePassengerAccount updatePassengerAccount, String email);

    void deleteAllPassengerAccounts();

    void deletePassengerAccountById(Long id);

    void deletePassengerAccountByEmail(String email);

    PassengerAccountDto getPassengerDataFromBookingSystem(String email);

}
