package pl.flywithbookedseats.passengeraccountservice.domain.passenger.service;

import org.springframework.data.domain.Pageable;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.UpdatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PagePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerAccountDto;

import java.util.List;

public interface PassengerAccountService {

    PagePassengerAccount retrieveAllPassengerAccountsFromDb(Pageable pageable);

    PassengerAccount retrievePassengerAccountById(Long id);

    PassengerAccount retrievePassengerAccountByEmail(String email);

    public PassengerAccount createNewPassengerAccount(PassengerAccount passengerAccount);

    PassengerAccount updatePassengerAccountById(long id, PassengerAccount passengerAccount);

    PassengerAccountDto updatePassengerAccountByEmail(UpdatePassengerAccountCommand updatePassengerAccountCommand, String email);

    void deleteAllPassengerAccounts();

    void deletePassengerAccountById(Long id);

    void deletePassengerAccountByEmail(String email);

    PassengerAccountDto getPassengerDataFromBookingSystem(String email);

}
