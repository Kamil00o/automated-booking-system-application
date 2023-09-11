package pl.flywithbookedseats.passengeraccountservice.service;

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
import pl.flywithbookedseats.passengeraccountservice.model.command.UpdatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.domain.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.repository.PassengerAccountRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PassengerAccountService {

    private static final Logger logger = LoggerFactory.getLogger(PassengerAccountController.class);
    private static final String PASSENGER_ACCOUNT_NOT_FOUND = "Passenger account with specified %s not found!";
    private static final String PASSENGER_ACCOUNT_WITH_SPECIFIED_EMAIL_EXISTS =
            "Passenger account with specified email: %s already exists!";

    private final PassengerAccountRepository passengerAccountRepository;

    @Transactional
    public List<PassengerAccount> retrieveAllPassengerAccountsFromDb() {
        return passengerAccountRepository.findAll();
    }

    public Optional<PassengerAccount> getPassengerAccountById(Long id) {
        Optional<PassengerAccount> passengerAccount = passengerAccountRepository.findById(id);

        if (passengerAccount.isEmpty()) {
            throw new PassengerAccountNotFoundException(
                    "Passenger account with specified id does not exist id: %s\", id");
        }

        return passengerAccount;
    }

    public ResponseEntity<Object> createNewPassengerAccount(PassengerAccount passengerAccount) {
        PassengerAccount savedPassengerAccount = passengerAccountRepository.save(passengerAccount);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPassengerAccount.getId())
                .toUri()
                .normalize();

        logger.info("New passenger account for {} {} is being created!", passengerAccount.getName()
                ,passengerAccount.getSurname());

        return ResponseEntity.created(location).build();
    }

    @Transactional
    public void editPassengerAccount(long id, UpdatePassengerAccount updatePassengerAccount) {
        PassengerAccount passengerAccount = passengerAccountRepository.findById(id)
                .orElseThrow(() -> new PassengerAccountNotFoundException(PASSENGER_ACCOUNT_NOT_FOUND.formatted(id)));

        if (!passengerAccountRepository.existsByEmail(updatePassengerAccount.email())) {
            passengerAccount.setName(updatePassengerAccount.name());
            passengerAccount.setSurname(updatePassengerAccount.surname());
            passengerAccount.setEmail(updatePassengerAccount.email());
            passengerAccount.setBirthDate(updatePassengerAccount.birthDate());
            passengerAccount.setHealthState(updatePassengerAccount.healthState());
            passengerAccount.setLifeStage(updatePassengerAccount.lifeStage());
        } else {
            throw new PassengerAccountAlreadyExistsException(PASSENGER_ACCOUNT_WITH_SPECIFIED_EMAIL_EXISTS
                    .formatted(updatePassengerAccount.email()));
        }
    }
}