package pl.flywithbookedseats.passengeraccountservice.controller;

import jakarta.persistence.Column;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.flywithbookedseats.passengeraccountservice.model.command.CreatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.domain.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.exceptions.PassengerAccountNotFoundException;
import pl.flywithbookedseats.passengeraccountservice.repository.PassengerAccountRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/passenger-account")
public class PassengerAccountController {

    private static final Logger logger = LoggerFactory.getLogger(PassengerAccountController.class);

    @Autowired
    private PassengerAccountRepository passengerAccountRepository;

    @GetMapping(path = "/test")
    public String getTestString() {
        return "test string";
    }

    @GetMapping(path = "/all")
    @Transactional
    public List<PassengerAccount> retrieveAllPassengerAccountsFromDb() {
        return passengerAccountRepository.findAll();
    }

    @GetMapping(path = "/id/{id}")
    public Optional<PassengerAccount> getPassengerAccountById(@PathVariable Long id) {
        Optional<PassengerAccount> passengerAccount = passengerAccountRepository.findById(id);

        if (passengerAccount.isEmpty()) {
            throw new PassengerAccountNotFoundException(
                    "Passenger account with specified id does not exist id: %s\", id");
        }

        return passengerAccount;
    }

    @PostMapping(path = "/create")
    @Transactional
    public ResponseEntity<Object> createNewPassengerAccount(@Valid @Argument CreatePassengerAccount createPassengerAccount) {

        PassengerAccount passengerAccount = PassengerAccount.builder()
                .name(createPassengerAccount.name())
                .surname(createPassengerAccount.surname())
                .email(createPassengerAccount.email())
                .birthDate(createPassengerAccount.birthDate())
                .healthState(createPassengerAccount.healthState())
                .lifeStage(createPassengerAccount.lifeStage())
                .build();

        passengerAccountRepository.save(passengerAccount);

        /*logger.info("New passenger account for {} {} is being created!", passengerAccount.getName()
                ,passengerAccount.getSurname());
        PassengerAccount savedPassengerAccount = passengerAccountRepository.save(passengerAccount);*/


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/../{id}")
                .buildAndExpand(passengerAccount.getId())
                .toUri()
                .normalize();
        return ResponseEntity.created(location).build();
    }
}
