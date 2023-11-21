package pl.flywithbookedseats.passengeraccountservice.appservices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.UpdatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper.CreatePassengerAccountEntityMapper;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.PassengerAccountService;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.PassengerAccountBusinessLogic;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.entity.PassengerAccountEntity;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.mapper.PassengerAccountEntityDtoMapper;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.repository.JpaPassengerAccountRepository;

import java.util.List;

@Service
@EnableFeignClients
@RequiredArgsConstructor
public class PassengerAccountApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(PassengerAccountApplicationService.class);


    private final JpaPassengerAccountRepository jpaPassengerAccountRepository;
    private final CreatePassengerAccountEntityMapper createPassengerAccountEntityMapper;
    private final PassengerAccountEntityDtoMapper passengerAccountEntityDtoMapper;
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
        jpaPassengerAccountRepository.deleteAll();
    }

    @Transactional
    public void deletePassengerAccountById(Long id) {
        passengerAccountBL.deletePassengerAccountById(id);
    }

    @Transactional
    public void deletePassengerAccountByEmail(String email) {
        passengerAccountBL.deletePassengerAccountByEmail(email);
    }

    public PassengerAccountDto getPassengerDataFromBookingSystem(String email) {
        return passengerAccountBL.getPassengerDataFromBookingService(email);
    }

    public List<PassengerAccountEntity> retrieveAllPassengerAccountsFromDb() {
        return jpaPassengerAccountRepository.findAll();
    }

    public PassengerAccount retrievePassengerAccountById(Long id) {
        return passengerAccountService.retrievePassengerAccountById(id);
    }

    public PassengerAccountDto retrievePassengerAccountByEmail(String email) {
        return passengerAccountEntityDtoMapper.apply(passengerAccountBL.retrievePassengerAccountFromDb(email));
    }
}
