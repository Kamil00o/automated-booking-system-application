package pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.PassengerAccountService;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.CreatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.UpdatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper.CreatePassengerAccountMapper;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper.PassengerAccountDtoMapper;
import pl.flywithbookedseats.passengeraccountservice.external.passenger.repository.PassengerAccountRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerAccountServiceImpl implements PassengerAccountService {

    private static final Logger logger = LoggerFactory.getLogger(PassengerAccountServiceImpl.class);


    private final PassengerAccountRepository passengerAccountRepository;
    private final CreatePassengerAccountMapper createPassengerAccountMapper;
    private final PassengerAccountDtoMapper passengerAccountDtoMapper;
    private final PassengerAccountBusinessLogic passengerAccountBL;

    @Transactional
    @Override
    public ResponseEntity<Object> createNewPassengerAccount(CreatePassengerAccountCommand createPassengerAccountCommand) {
        return passengerAccountBL.generateResponseEntity(passengerAccountBL
                .generateNewPassengerAccount(createPassengerAccountCommand));
    }

    @Transactional
    @Override
    public PassengerAccountDto updatePassengerAccountById(long id, UpdatePassengerAccount updatePassengerAccount) {
        return passengerAccountDtoMapper.apply(passengerAccountBL
                .updateSpecifiedPassengerAccount(updatePassengerAccount, passengerAccountBL
                        .retrievePassengerAccountFromDb(id)));
    }

    @Transactional
    @Override
    public PassengerAccountDto updatePassengerAccountByEmail(UpdatePassengerAccount updatePassengerAccount, String email) {
        return passengerAccountDtoMapper.apply(passengerAccountBL
                .updateSpecifiedPassengerAccount(updatePassengerAccount, passengerAccountBL
                        .retrievePassengerAccountFromDb(email)));
    }

    @Transactional
    @Override
    public void deleteAllPassengerAccounts() {
        passengerAccountRepository.deleteAll();
    }

    @Transactional
    @Override
    public void deletePassengerAccountById(Long id) {
        passengerAccountBL.deletePassengerAccountById(id);
    }

    @Transactional
    @Override
    public void deletePassengerAccountByEmail(String email) {
        passengerAccountBL.deletePassengerAccountByEmail(email);
    }

    public PassengerAccountDto getPassengerDataFromBookingSystem(String email) {
        return passengerAccountBL.getPassengerDataFromBookingService(email);
    }

    @Override
    public List<PassengerAccount> retrieveAllPassengerAccountsFromDb() {
        return passengerAccountRepository.findAll();
    }

    @Override
    public PassengerAccountDto retrievePassengerAccountById(Long id) {
        return passengerAccountDtoMapper.apply(passengerAccountBL.retrievePassengerAccountFromDb(id));
    }

    @Override
    public PassengerAccountDto retrievePassengerAccountByEmail(String email) {
        return passengerAccountDtoMapper.apply(passengerAccountBL.retrievePassengerAccountFromDb(email));
    }
}
