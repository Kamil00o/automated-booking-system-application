package pl.flywithbookedseats.appservices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.domain.passenger.PagePassenger;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.PassengerService;

@Service
@RequiredArgsConstructor
public class PassengerApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(PassengerApplicationService.class);

    private final PassengerService service;

    @Transactional
    public Passenger createNewPassengerAccount(Passenger passenger) {
        return service.createNewPassenger(passenger);
    }

    @Transactional
    public Passenger updatePassengerAccountById(long id, Passenger passenger) {
        return service.updatePassengerById(id, passenger);
    }

/*    @Transactional
    public PassengerDto updatePassengerAccountByEmail(UpdatePassengerCommand updatePassengerAccountCommand, String email) {
        return passengerAccountEntityDtoMapper.apply(passengerAccountBL
                .updateSpecifiedPassengerAccount(updatePassengerAccountCommand, passengerAccountBL
                        .retrievePassengerAccountFromDb(email)));
    }*/

    @Transactional
    public void deleteAllPassengerAccounts() {
        service.deleteAllPassengerAccounts();
    }

    @Transactional
    public void deletePassengerAccountById(Long id) {
        service.deletePassengerAccountById(id);
    }

    @Transactional
    public void deletePassengerAccountByEmail(String email) {
        service.deletePassengerAccountByEmail(email);
    }

    public Passenger getPassengerDataFromBookingSystem(String email) {
        return service.getPassengerDataFromBookingSystem(email);
    }

    public PagePassenger retrieveAllPassengerAccountsFromDb(Pageable pageable) {
        return service.retrieveAllPassengersFromDb(pageable);
    }

    public Passenger retrievePassengerAccountById(Long id) {
        return service.retrievePassengerById(id);
    }

    public Passenger retrievePassengerAccountByEmail(String email) {
        return service.retrievePassengerByEmail(email);
    }
}
