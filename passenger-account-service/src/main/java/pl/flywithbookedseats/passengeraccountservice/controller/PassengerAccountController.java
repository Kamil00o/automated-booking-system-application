package pl.flywithbookedseats.passengeraccountservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.passengeraccountservice.model.command.CreatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.model.command.UpdatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.domain.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.dto.PassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.service.PassengerAccountService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/passengers")
public class PassengerAccountController {

    private static final Logger logger = LoggerFactory.getLogger(PassengerAccountController.class);

    private final PassengerAccountService passengerAccountService;

    @GetMapping(path = "/test")
    public String getTestString() {
        return "test string";
    }

    @PostMapping
    public ResponseEntity<Object> createNewPassengerAccount(
            @Valid @RequestBody CreatePassengerAccountCommand createPassengerAccountCommand) {
        return passengerAccountService.createNewPassengerAccount(createPassengerAccountCommand);
    }

    @PutMapping(path = "/{id}")
    public PassengerAccountDto updatePassengerAccountById(@PathVariable long id,
                                           @Valid @RequestBody UpdatePassengerAccount updatePassengerAccount) {
        logger.info("Editing passenger account for ID: {}:", id);
        return passengerAccountService.updatePassengerAccountById(id, updatePassengerAccount);
    }

    /*@PutMapping(path = "/edit/email/{email}")
    public PassengerAccountDto updatePassengerAccountByEmail(
            @Valid @RequestBody UpdatePassengerAccount updatePassengerAccount,
            @PathVariable String email) {
        logger.info("Editing passenger account for email: {}:", email);
        return passengerAccountService.updatePassengerAccountByEmail(updatePassengerAccount, email);
    }*/

    @GetMapping
    public List<PassengerAccount> retrieveAllPassengerAccountsFromDb() {
        return passengerAccountService.retrieveAllPassengerAccountsFromDb();
    }

    @GetMapping(path = "/{id}")
    public PassengerAccountDto retrievePassengerAccountById(@PathVariable Long id) {
        logger.info("Retrieving passenger account for ID: {}:", id);
        return passengerAccountService.retrievePassengerAccountById(id);
    }

    /*@GetMapping(path = "/get/email/{email}")
    public PassengerAccountDto retrievePassengerAccountByEmail(@PathVariable String email) {
        logger.info("Retrieving passenger account for email: {}:", email);
        return passengerAccountService.retrievePassengerAccountByEmail(email);
    }*/

    @DeleteMapping
    public void deleteAllPassengerAccounts() {
        passengerAccountService.deleteAllPassengerAccounts();
    }

    @DeleteMapping(path = "/{id}")
    public void deletePassengerAccountById(@PathVariable Long id) {
        passengerAccountService.deletePassengerAccountById(id);
    }

    /*@DeleteMapping(path = "/delete/email/{email}")
    public void deletePassengerAccountByEmail(@PathVariable String email) {
        passengerAccountService.deletePassengerAccountByEmail(email);
    }*/

    @GetMapping(path = "/seats-booking/{email}")
    public PassengerAccountDto getPassengerDataFromBookingSystem(@PathVariable String email) {
        return passengerAccountService.getPassengerDataFromBookingSystem(email);
    }
}
