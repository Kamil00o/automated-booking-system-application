package pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.PagePassenger;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.Passenger;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.PassengerRepository;

@RequiredArgsConstructor
public class PassengerService {

   private final PassengerBusinessLogic passengerAccountBL;
   private final PassengerRepository passengerRepository;

    public PagePassenger retrieveAllPassengerAccountsFromDb(Pageable pageable) {
        return passengerRepository.findAll(pageable);
    }

    public Passenger retrievePassengerAccountById(Long id) {
        return passengerAccountBL.retrievePassengerAccountFromDb(id);
    }

    public Passenger retrievePassengerAccountByEmail(String email) {
        return passengerAccountBL.retrievePassengerAccountFromDb(email);
    }

    public Passenger createNewPassengerAccount(Passenger passenger) {
        return passengerAccountBL.generateNewPassengerAccount(passenger);
    }

    public Passenger updatePassengerAccountById(Long id, Passenger passenger) {
        return passengerAccountBL.updatePassengerAccountById(id, passenger);
    }

    public Passenger updatePassengerAccountByEmail(String email, Passenger passenger) {
        return passengerAccountBL.updatePassengerAccountByEmail(email, passenger);
    }

    public void deleteAllPassengerAccounts() {
        passengerRepository.deleteAll();
    }

    public void deletePassengerAccountById(Long id) {
        passengerAccountBL.deletePassengerAccountById(id);
    }

    public void deletePassengerAccountByEmail(String email) {

    }

    public Passenger getPassengerDataFromBookingSystem(String email) {
        return passengerAccountBL.getPassengerDataFromBookingService(email);
    }
}
