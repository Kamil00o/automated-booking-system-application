package pl.flywithbookedseats.passengeraccountservice.service.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.passengeraccountservice.exceptions.PassengerAccountAlreadyExistsException;
import pl.flywithbookedseats.passengeraccountservice.exceptions.PassengerAccountNotFoundException;
import pl.flywithbookedseats.passengeraccountservice.model.command.CreatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.command.UpdatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.domain.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.dto.PassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.model.mapper.CreatePassengerAccountMapper;
import pl.flywithbookedseats.passengeraccountservice.model.mapper.PassengerAccountDtoMapper;
import pl.flywithbookedseats.passengeraccountservice.repository.PassengerAccountRepository;
import pl.flywithbookedseats.passengeraccountservice.service.PassengerAccountService;

import java.util.List;

import static pl.flywithbookedseats.passengeraccountservice.service.implementation.PassengerAccountConsts.*;

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

    @Transactional
    @Override
    public ResponseEntity<Object> createNewPassengerAccount(CreatePassengerAccount createPassengerAccount) {
        return passengerAccountBL.generateResponseEntity(passengerAccountBL
                .generateNewPassengerAccount(createPassengerAccount));
    }

    @Transactional
    @Override
    public void editPassengerAccount(long id, UpdatePassengerAccount updatePassengerAccount) {
        PassengerAccount passengerAccount = passengerAccountRepository.findById(id)
                .orElseThrow(() -> new PassengerAccountNotFoundException(PASSENGER_ACCOUNT_NOT_FOUND_ID.formatted(id)));

        if (!passengerAccountBL.exists(updatePassengerAccount)) {
            passengerAccount.setName(updatePassengerAccount.name());
            passengerAccount.setSurname(updatePassengerAccount.surname());
            passengerAccount.setEmail(updatePassengerAccount.email());
            passengerAccount.setBirthDate(updatePassengerAccount.birthDate());
            passengerAccount.setDisability(updatePassengerAccount.disability());
            passengerAccount.setReservationIdList(updatePassengerAccount.reservationIdList());
        } else {
            throw new PassengerAccountAlreadyExistsException(PASSENGER_ACCOUNT_WITH_SPECIFIED_EMAIL_EXISTS
                    .formatted(updatePassengerAccount.email()));
        }
    }


}
