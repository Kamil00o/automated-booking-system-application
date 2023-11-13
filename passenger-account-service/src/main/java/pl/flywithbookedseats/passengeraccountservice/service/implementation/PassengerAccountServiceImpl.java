package pl.flywithbookedseats.passengeraccountservice.service.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.passengeraccountservice.model.command.CreatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.command.UpdatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.domain.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.dto.PassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.model.mapper.CreatePassengerAccountMapper;
import pl.flywithbookedseats.passengeraccountservice.model.mapper.PassengerAccountDtoMapper;
import pl.flywithbookedseats.passengeraccountservice.repository.PassengerAccountRepository;
import pl.flywithbookedseats.passengeraccountservice.service.PassengerAccountService;

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
    public ResponseEntity<Object> createNewPassengerAccount(CreatePassengerAccount createPassengerAccount) {
        return passengerAccountBL.generateResponseEntity(passengerAccountBL
                .generateNewPassengerAccount(createPassengerAccount));
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
