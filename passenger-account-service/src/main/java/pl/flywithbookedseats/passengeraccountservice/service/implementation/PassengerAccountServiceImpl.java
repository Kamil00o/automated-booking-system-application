package pl.flywithbookedseats.passengeraccountservice.service.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.flywithbookedseats.passengeraccountservice.controller.PassengerAccountController;
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

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerAccountServiceImpl implements PassengerAccountService {

    private static final Logger logger = LoggerFactory.getLogger(PassengerAccountController.class);
    private static final String PASSENGER_ACCOUNT_NOT_FOUND = "Passenger account with specified %s not found!";
    private static final String PASSENGER_ACCOUNT_WITH_SPECIFIED_EMAIL_EXISTS =
            "Passenger account with specified email: %s already exists!";

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
        if (!passengerAccountBL.exists(createPassengerAccount)) {
            PassengerAccount savedPassengerAccount = createPassengerAccountMapper
                    .apply(createPassengerAccount);

            passengerAccountRepository.save(savedPassengerAccount);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedPassengerAccount.getId())
                    .toUri()
                    .normalize();

            logger.info("New passenger account for {} {} is being created!", savedPassengerAccount.getName()
                    , savedPassengerAccount.getSurname());

            return ResponseEntity.created(location).build();
        } else {
            throw new PassengerAccountAlreadyExistsException(PASSENGER_ACCOUNT_WITH_SPECIFIED_EMAIL_EXISTS
                    .formatted(createPassengerAccount.email()));
        }
    }

    @Transactional
    @Override
    public void editPassengerAccount(long id, UpdatePassengerAccount updatePassengerAccount) {
        PassengerAccount passengerAccount = passengerAccountRepository.findById(id)
                .orElseThrow(() -> new PassengerAccountNotFoundException(PASSENGER_ACCOUNT_NOT_FOUND.formatted(id)));

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
