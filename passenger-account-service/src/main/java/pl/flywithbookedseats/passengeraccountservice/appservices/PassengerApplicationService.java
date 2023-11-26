package pl.flywithbookedseats.passengeraccountservice.appservices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PagePassenger;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.Passenger;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.PassengerService;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.PassengerBusinessLogic;

@Service
@RequiredArgsConstructor
public class PassengerApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(PassengerApplicationService.class);

    private final PassengerBusinessLogic passengerAccountBL;
    private final PassengerService passengerService;

    @Transactional
    public Passenger createNewPassengerAccount(Passenger passenger) {
        return passengerService.createNewPassengerAccount(passenger);
    }

    @Transactional
    public Passenger updatePassengerAccountById(long id, Passenger passenger) {
        return passengerService.updatePassengerAccountById(id, passenger);
    }

/*    @Transactional
    public PassengerDto updatePassengerAccountByEmail(UpdatePassengerCommand updatePassengerAccountCommand, String email) {
        return passengerAccountEntityDtoMapper.apply(passengerAccountBL
                .updateSpecifiedPassengerAccount(updatePassengerAccountCommand, passengerAccountBL
                        .retrievePassengerAccountFromDb(email)));
    }*/

    @Transactional
    public void deleteAllPassengerAccounts() {
        passengerService.deleteAllPassengerAccounts();
    }

    @Transactional
    public void deletePassengerAccountById(Long id) {
        passengerService.deletePassengerAccountById(id);
    }

    @Transactional
    public void deletePassengerAccountByEmail(String email) {
        passengerAccountBL.deletePassengerAccountByEmail(email);
    }

    public Passenger getPassengerDataFromBookingSystem(String email) {
        return passengerService.getPassengerDataFromBookingSystem(email);
    }

    public PagePassenger retrieveAllPassengerAccountsFromDb(Pageable pageable) {
        return passengerService.retrieveAllPassengerAccountsFromDb(pageable);
    }

    public Passenger retrievePassengerAccountById(Long id) {
        return passengerService.retrievePassengerAccountById(id);
    }

    public Passenger retrievePassengerAccountByEmail(String email) {
        return passengerService.retrievePassengerAccountByEmail(email);
    }
}
