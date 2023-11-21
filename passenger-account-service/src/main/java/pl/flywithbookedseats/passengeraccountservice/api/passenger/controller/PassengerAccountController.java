package pl.flywithbookedseats.passengeraccountservice.api.passenger.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.CreatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.UpdatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper.CreatePassengerAccountMapper;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper.PassengerAccountDtoMapper;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper.UpdatePassengerAccountMapper;
import pl.flywithbookedseats.passengeraccountservice.appservices.PassengerAccountApplicationService;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.entity.PassengerAccountEntity;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.PassengerAccountService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@EnableFeignClients
@RequestMapping(path = "/passengers")
public class PassengerAccountController {

    private static final Logger logger = LoggerFactory.getLogger(PassengerAccountController.class);

    private final PassengerAccountService passengerAccountService;
    private final PassengerAccountApplicationService service;
    private final PassengerAccountDtoMapper passengerAccountDtoMapper;
    private final CreatePassengerAccountMapper createPassengerAccountMapper;
    private final UpdatePassengerAccountMapper updatePassengerAccountMapper;

    @GetMapping(path = "/test")
    public String getTestString() {
        return "test string";
    }

    @PostMapping
    public ResponseEntity<PassengerAccountDto> createNewPassengerAccount(
            @Valid @RequestBody CreatePassengerAccountCommand createPassengerAccountCommand) {
        PassengerAccount passengerAccount = service.
                createNewPassengerAccount(createPassengerAccountMapper.toDomain(createPassengerAccountCommand));
        return ResponseEntity.ok(passengerAccountDtoMapper.toDto(passengerAccount));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PassengerAccountDto> updatePassengerAccountById(
            @PathVariable long id,
            @Valid @RequestBody UpdatePassengerAccountCommand updatePassengerAccountCommand) {
        logger.info("Editing passenger account for ID: {}:", id);
        PassengerAccount savedPassengerAccount = service
                .updatePassengerAccountById(id, updatePassengerAccountMapper.toDomain(updatePassengerAccountCommand));

        return ResponseEntity.ok(passengerAccountDtoMapper.toDto(savedPassengerAccount));
    }

    /*@PutMapping(path = "/edit/email/{email}")
    public PassengerAccountDtoMapper updatePassengerAccountByEmail(
            @Valid @RequestBody UpdatePassengerAccountCommand updatePassengerAccount,
            @PathVariable String email) {
        logger.info("Editing passenger account for email: {}:", email);
        return passengerAccountService.updatePassengerAccountByEmail(updatePassengerAccount, email);
    }*/

    @GetMapping
    public List<PassengerAccountEntity> retrieveAllPassengerAccountsFromDb() {
        return passengerAccountService.retrieveAllPassengerAccountsFromDb();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PassengerAccountDto> retrievePassengerAccountById(@PathVariable Long id) {
        logger.info("Retrieving passenger account for ID: {}:", id);
        PassengerAccountDto savedPassengerAccountDto = passengerAccountService.retrievePassengerAccountById(id);
        return ResponseEntity.ok(savedPassengerAccountDto);
    }

    /*@GetMapping(path = "/get/email/{email}")
    public PassengerAccountDtoMapper retrievePassengerAccountByEmail(@PathVariable String email) {
        logger.info("Retrieving passenger account for email: {}:", email);
        return passengerAccountService.retrievePassengerAccountByEmail(email);
    }*/

    @DeleteMapping
    public ResponseEntity<Void> deleteAllPassengerAccounts() {
        passengerAccountService.deleteAllPassengerAccounts();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePassengerAccountById(@PathVariable Long id) {
        passengerAccountService.deletePassengerAccountById(id);
        return ResponseEntity.ok().build();
    }

    /*@DeleteMapping(path = "/delete/email/{email}")
    public void deletePassengerAccountByEmail(@PathVariable String email) {
        passengerAccountService.deletePassengerAccountByEmail(email);
    }*/

    @GetMapping(path = "/seats-booking/{email}")
    public ResponseEntity<PassengerAccountDto> getPassengerDataFromBookingSystem(@PathVariable String email) {
        PassengerAccountDto savedPassengerAccountDto = passengerAccountService.getPassengerDataFromBookingSystem(email);
        return ResponseEntity.ok(savedPassengerAccountDto);
    }
}
