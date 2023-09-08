package pl.flywithbookedseats.passengeraccountservice.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.flywithbookedseats.passengeraccountservice.model.domain.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.repository.PassengerAccountRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
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
    public List<PassengerAccount> retrieveAllPassengerAccountsFromDb() {
        return passengerAccountRepository.findAll();
    }

    @GetMapping(path = "/id/{id}")
    public Optional<PassengerAccount> getPassengerAccountById(@PathVariable Long id) {
        Optional<PassengerAccount> passengerAccount = passengerAccountRepository.findById(id);
        return passengerAccount;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Object> createNewPassengerAccount(@Valid @RequestBody PassengerAccount passengerAccount) {
        logger.info("Request added!!!!");
        PassengerAccount savedPassengerAccount = passengerAccountRepository.save(passengerAccount);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/../{id}")
                .buildAndExpand(savedPassengerAccount.getId())
                .toUri()
                .normalize();
        return ResponseEntity.created(location).build();
    }
}
