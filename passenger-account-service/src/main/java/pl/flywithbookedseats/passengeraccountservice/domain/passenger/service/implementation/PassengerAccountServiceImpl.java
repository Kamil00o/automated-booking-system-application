package pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation;

import lombok.RequiredArgsConstructor;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.UpdatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.PassengerAccountService;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.entity.PassengerAccountEntity;

import java.util.List;

@RequiredArgsConstructor
public class PassengerAccountServiceImpl implements PassengerAccountService {

   private final PassengerAccountBusinessLogic passengerAccountBL;

    @Override
    public List<PassengerAccountEntity> retrieveAllPassengerAccountsFromDb() {
        return null;
    }

    @Override
    public PassengerAccountDto retrievePassengerAccountById(Long id) {
        return null;
    }

    @Override
    public PassengerAccountDto retrievePassengerAccountByEmail(String email) {
        return null;
    }

    @Override
    public PassengerAccount createNewPassengerAccount(PassengerAccount passengerAccount) {
        return passengerAccountBL.generateNewPassengerAccount(passengerAccount);
    }

    @Override
    public PassengerAccountDto updatePassengerAccountById(long id, UpdatePassengerAccountCommand updatePassengerAccountCommand) {
        return null;
    }

    @Override
    public PassengerAccountDto updatePassengerAccountByEmail(UpdatePassengerAccountCommand updatePassengerAccountCommand, String email) {
        return null;
    }

    @Override
    public void deleteAllPassengerAccounts() {

    }

    @Override
    public void deletePassengerAccountById(Long id) {

    }

    @Override
    public void deletePassengerAccountByEmail(String email) {

    }

    @Override
    public PassengerAccountDto getPassengerDataFromBookingSystem(String email) {
        return null;
    }
}
