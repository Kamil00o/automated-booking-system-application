package pl.flywithbookedseats.passengeraccountservice.service.implementation;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.flywithbookedseats.passengeraccountservice.exceptions.PassengerAccountAlreadyExistsException;
import pl.flywithbookedseats.passengeraccountservice.exceptions.PassengerAccountNotFoundException;
import pl.flywithbookedseats.passengeraccountservice.model.command.CreatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.command.UpdatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.domain.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.mapper.CreatePassengerAccountMapper;
import pl.flywithbookedseats.passengeraccountservice.repository.PassengerAccountRepository;

import java.net.URI;

import static pl.flywithbookedseats.passengeraccountservice.service.implementation.PassengerAccountConsts.*;

@AllArgsConstructor
@Component
public class PassengerAccountBusinessLogic {

    private static final Logger logger = LoggerFactory.getLogger(PassengerAccountBusinessLogic.class);

    private final PassengerAccountRepository passengerAccountRepository;
    private final CreatePassengerAccountMapper createPassengerAccountMapper;

    public PassengerAccount generateNewPassengerAccount(CreatePassengerAccount createPassengerAccount) {
        if (!exists(createPassengerAccount)) {
            PassengerAccount savedPassengerAccount = createPassengerAccountMapper.apply(createPassengerAccount);
            passengerAccountRepository.save(savedPassengerAccount);
            return savedPassengerAccount;
        } else {
            throw new PassengerAccountAlreadyExistsException(PASSENGER_ACCOUNT_WITH_SPECIFIED_EMAIL_EXISTS
                    .formatted(createPassengerAccount.email()));
        }
    }

    public ResponseEntity<Object> generateResponseEntity(PassengerAccount passengerAccount) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(passengerAccount.getId())
                .toUri()
                .normalize();

        logger.info("New passenger account for {} {} is being created!", passengerAccount.getName(),
                passengerAccount.getSurname());

        return ResponseEntity.created(location).build();
    }

    public PassengerAccount retrievePassengerAccountFromDb(Long id) {
        return passengerAccountRepository.findById(id)
                .orElseThrow(() -> new PassengerAccountNotFoundException(PASSENGER_ACCOUNT_NOT_FOUND_ID
                        .formatted(id)));
    }
    public PassengerAccount retrievePassengerAccountFromDb(String email) {
        return passengerAccountRepository.findByEmail(email)
                .orElseThrow(() -> new PassengerAccountNotFoundException(PASSENGER_ACCOUNT_NOT_FOUND_EMAIL
                        .formatted(email)));
    }

    public boolean exists(CreatePassengerAccount createPassengerAccount) {
        return passengerAccountRepository.existsByEmail(createPassengerAccount.email());
    }

    public boolean exists(UpdatePassengerAccount updatePassengerAccount) {
        return passengerAccountRepository.existsByEmail(updatePassengerAccount.email());
    }
}
