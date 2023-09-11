package pl.flywithbookedseats.passengeraccountservice.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.passengeraccountservice.model.command.UpdatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.domain.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.service.PassengerAccountService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/passenger-account")
public class PassengerAccountController {

    private final PassengerAccountService passengerAccountService;

    @GetMapping(path = "/test")
    public String getTestString() {
        return "test string";
    }

    @GetMapping(path = "/all")
    public List<PassengerAccount> retrieveAllPassengerAccountsFromDb() {
        return passengerAccountService.retrieveAllPassengerAccountsFromDb();
    }

    @GetMapping(path = "/id/{id}")
    public Optional<PassengerAccount> getPassengerAccountById(@PathVariable Long id) {
        return passengerAccountService.getPassengerAccountById(id);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Object> createNewPassengerAccount(@Valid @RequestBody PassengerAccount passengerAccount) {
        return passengerAccountService.createNewPassengerAccount(passengerAccount);
    }

    @PutMapping(path = "/edit/{id}")
    @Transactional
    public void editPassengerAccount(@PathVariable long id,
                                     @Valid @RequestBody UpdatePassengerAccount updatePassengerAccount) {
        passengerAccountService.editPassengerAccount(id, updatePassengerAccount);
    }
}
