package pl.flywithbookedseats.passengeraccountservice.domain.passenger.service;

import org.springframework.http.ResponseEntity;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.CreatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.UpdatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerAccountDto;

import java.util.List;

public interface PassengerAccountService {

    List<PassengerAccount> retrieveAllPassengerAccountsFromDb();

    PassengerAccountDto retrievePassengerAccountById(Long id);

    PassengerAccountDto retrievePassengerAccountByEmail(String email);

    public PassengerAccountDto createNewPassengerAccount(CreatePassengerAccountCommand createPassengerAccountCommand);

    PassengerAccountDto updatePassengerAccountById(long id, UpdatePassengerAccountCommand updatePassengerAccountCommand);

    PassengerAccountDto updatePassengerAccountByEmail(UpdatePassengerAccountCommand updatePassengerAccountCommand, String email);

    void deleteAllPassengerAccounts();

    void deletePassengerAccountById(Long id);

    void deletePassengerAccountByEmail(String email);

    PassengerAccountDto getPassengerDataFromBookingSystem(String email);

}
