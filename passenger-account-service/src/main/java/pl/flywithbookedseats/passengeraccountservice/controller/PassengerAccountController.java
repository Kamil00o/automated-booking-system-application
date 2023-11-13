package pl.flywithbookedseats.passengeraccountservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.passengeraccountservice.model.command.CreatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.command.UpdatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.domain.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.dto.PassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.service.implementation.PassengerAccountServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/passenger-account")
public class PassengerAccountController {

    private static final Logger logger = LoggerFactory.getLogger(PassengerAccountController.class);

    private final PassengerAccountServiceImpl passengerAccountServiceImpl;

    @GetMapping(path = "/test")
    public String getTestString() {
        return "test string";
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Object> createNewPassengerAccount(@Valid @RequestBody CreatePassengerAccount createPassengerAccount) {
        return passengerAccountServiceImpl.createNewPassengerAccount(createPassengerAccount);
    }

    @PutMapping(path = "/edit/id/{id}")
    public PassengerAccountDto updatePassengerAccountById(@PathVariable long id,
                                           @Valid @RequestBody UpdatePassengerAccount updatePassengerAccount) {
        logger.info("Editing passenger account for ID: {}:", id);
        return passengerAccountServiceImpl.updatePassengerAccountById(id, updatePassengerAccount);
    }

    @PutMapping(path = "/edit/email/{email}")
    public PassengerAccountDto updatePassengerAccountByEmail(
            @Valid @RequestBody UpdatePassengerAccount updatePassengerAccount,
            @PathVariable String email) {
        logger.info("Editing passenger account for email: {}:", email);
        return passengerAccountServiceImpl.updatePassengerAccountByEmail(updatePassengerAccount, email);
    }

    @GetMapping(path = "/all")
    public List<PassengerAccount> retrieveAllPassengerAccountsFromDb() {
        return passengerAccountServiceImpl.retrieveAllPassengerAccountsFromDb();
    }

    @GetMapping(path = "/get/id/{id}")
    public PassengerAccountDto retrievePassengerAccountById(@PathVariable Long id) {
        logger.info("Retrieving passenger account for ID: {}:", id);
        return passengerAccountServiceImpl.retrievePassengerAccountById(id);
    }

    @GetMapping(path = "/get/email/{email}")
    public PassengerAccountDto retrievePassengerAccountByEmail(@PathVariable String email) {
        logger.info("Retrieving passenger account for email: {}:", email);
        return passengerAccountServiceImpl.retrievePassengerAccountByEmail(email);
    }
}
