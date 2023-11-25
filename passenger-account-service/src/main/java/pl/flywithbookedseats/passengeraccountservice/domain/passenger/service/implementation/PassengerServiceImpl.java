package pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PagePassenger;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.Passenger;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.PassengerRepository;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.PassengerService;

@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

   private final PassengerBusinessLogic passengerAccountBL;
   private final PassengerRepository passengerRepository;

    @Override
    public PagePassenger retrieveAllPassengerAccountsFromDb(Pageable pageable) {
        return passengerRepository.findAll(pageable);
    }

    @Override
    public Passenger retrievePassengerAccountById(Long id) {
        return passengerAccountBL.retrievePassengerAccountFromDb(id);
    }

    @Override
    public Passenger retrievePassengerAccountByEmail(String email) {
        return passengerAccountBL.retrievePassengerAccountFromDb(email);
    }

    @Override
    public Passenger createNewPassengerAccount(Passenger passenger) {
        return passengerAccountBL.generateNewPassengerAccount(passenger);
    }

    @Override
    public Passenger updatePassengerAccountById(Long id, Passenger passenger) {
        return passengerAccountBL.updatePassengerAccountById(id, passenger);
    }

    @Override
    public Passenger updatePassengerAccountByEmail(String email, Passenger passenger) {
        return passengerAccountBL.updatePassengerAccountByEmail(email, passenger);
    }

    @Override
    public void deleteAllPassengerAccounts() {
        passengerRepository.deleteAll();
    }

    @Override
    public void deletePassengerAccountById(Long id) {
        passengerAccountBL.deletePassengerAccountById(id);
    }

    @Override
    public void deletePassengerAccountByEmail(String email) {

    }

    @Override
    public Passenger getPassengerDataFromBookingSystem(String email) {
        return passengerAccountBL.getPassengerDataFromBookingService(email);
    }
}
