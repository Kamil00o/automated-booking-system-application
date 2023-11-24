package pl.flywithbookedseats.passengeraccountservice.appservices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PagePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.PassengerAccountService;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.PassengerAccountBusinessLogic;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.repository.JpaPassengerAccountRepository;

@Service
@RequiredArgsConstructor
public class PassengerAccountApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(PassengerAccountApplicationService.class);


    private final JpaPassengerAccountRepository jpaPassengerAccountRepository;
    private final PassengerAccountBusinessLogic passengerAccountBL;
    private final PassengerAccountService passengerAccountService;

    @Transactional
    public PassengerAccount createNewPassengerAccount(PassengerAccount passengerAccount) {
        return passengerAccountService.createNewPassengerAccount(passengerAccount);
    }

    @Transactional
    public PassengerAccount updatePassengerAccountById(long id, PassengerAccount passengerAccount) {
        return passengerAccountService.updatePassengerAccountById(id, passengerAccount);
    }

/*    @Transactional
    public PassengerAccountDto updatePassengerAccountByEmail(UpdatePassengerAccountCommand updatePassengerAccountCommand, String email) {
        return passengerAccountEntityDtoMapper.apply(passengerAccountBL
                .updateSpecifiedPassengerAccount(updatePassengerAccountCommand, passengerAccountBL
                        .retrievePassengerAccountFromDb(email)));
    }*/

    @Transactional
    public void deleteAllPassengerAccounts() {
        passengerAccountService.deleteAllPassengerAccounts();
    }

    @Transactional
    public void deletePassengerAccountById(Long id) {
        passengerAccountService.deletePassengerAccountById(id);
    }

    @Transactional
    public void deletePassengerAccountByEmail(String email) {
        passengerAccountBL.deletePassengerAccountByEmail(email);
    }

    public PassengerAccount getPassengerDataFromBookingSystem(String email) {
        return passengerAccountBL.getPassengerDataFromBookingService(email);
    }

    public PagePassengerAccount retrieveAllPassengerAccountsFromDb(Pageable pageable) {
        return passengerAccountService.retrieveAllPassengerAccountsFromDb(pageable);
    }

    public PassengerAccount retrievePassengerAccountById(Long id) {
        return passengerAccountService.retrievePassengerAccountById(id);
    }

    public PassengerAccount retrievePassengerAccountByEmail(String email) {
        return passengerAccountService.retrievePassengerAccountByEmail(email);
    }
}
