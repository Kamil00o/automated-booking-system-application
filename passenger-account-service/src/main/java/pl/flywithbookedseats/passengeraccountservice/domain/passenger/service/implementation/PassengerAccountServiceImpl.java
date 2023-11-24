package pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.UpdatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PagePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.PassengerAccountRepository;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.PassengerAccountService;

@RequiredArgsConstructor
public class PassengerAccountServiceImpl implements PassengerAccountService {

   private final PassengerAccountBusinessLogic passengerAccountBL;
   private final PassengerAccountRepository passengerAccountRepository;

    @Override
    public PagePassengerAccount retrieveAllPassengerAccountsFromDb(Pageable pageable) {
        return passengerAccountRepository.findAll(pageable);
    }

    @Override
    public PassengerAccount retrievePassengerAccountById(Long id) {
        return passengerAccountBL.retrievePassengerAccountFromDb(id);
    }

    @Override
    public PassengerAccount retrievePassengerAccountByEmail(String email) {
        return passengerAccountBL.retrievePassengerAccountFromDb(email);
    }

    @Override
    public PassengerAccount createNewPassengerAccount(PassengerAccount passengerAccount) {
        return passengerAccountBL.generateNewPassengerAccount(passengerAccount);
    }

    @Override
    public PassengerAccount updatePassengerAccountById(long id, PassengerAccount passengerAccount) {
        return passengerAccountBL.updateSpecifiedPassengerAccount(id, passengerAccount);
    }

    @Override
    public PassengerAccountDto updatePassengerAccountByEmail(UpdatePassengerAccountCommand updatePassengerAccountCommand, String email) {
        return null;
    }

    @Override
    public void deleteAllPassengerAccounts() {
        passengerAccountRepository.deleteAll();
    }

    @Override
    public void deletePassengerAccountById(Long id) {
        passengerAccountBL.deletePassengerAccountById(id);
    }

    @Override
    public void deletePassengerAccountByEmail(String email) {

    }

    @Override
    public PassengerAccountDto getPassengerDataFromBookingSystem(String email) {
        return null;
    }
}
