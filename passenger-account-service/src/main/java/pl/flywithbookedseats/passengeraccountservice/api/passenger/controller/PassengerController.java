package pl.flywithbookedseats.passengeraccountservice.api.passenger.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.CreatePassengerCommand;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.UpdatePassengerCommand;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PagePassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper.CreatePassengerMapper;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper.PagePassengerDtoMapper;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper.PassengerDtoMapper;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper.UpdatePassengerMapper;
import pl.flywithbookedseats.passengeraccountservice.appservices.PassengerApplicationService;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.Passenger;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/passengers")
public class PassengerController {

    private static final Logger logger = LoggerFactory.getLogger(PassengerController.class);

    private final PassengerApplicationService service;
    private final PassengerDtoMapper passengerDtoMapper;
    private final CreatePassengerMapper createPassengerMapper;
    private final UpdatePassengerMapper updatePassengerMapper;
    private final PagePassengerDtoMapper pagePassengerDtoMapper;

    @GetMapping(path = "/test")
    public String getTestString() {
        return "test string";
    }

    @PostMapping
    public ResponseEntity<PassengerDto> createNewPassengerAccount(
            @Valid @RequestBody CreatePassengerCommand createPassengerCommand) {
        Passenger passenger = service.
                createNewPassengerAccount(createPassengerMapper.toDomain(createPassengerCommand));

        return ResponseEntity.ok(passengerDtoMapper.toDto(passenger));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PassengerDto> updatePassengerAccountById(
            @PathVariable long id,
            @Valid @RequestBody UpdatePassengerCommand updatePassengerCommand) {
        logger.info("Editing passenger account for ID: {}:", id);
        Passenger savedPassenger = service
                .updatePassengerAccountById(id, updatePassengerMapper.toDomain(updatePassengerCommand));

        return ResponseEntity.ok(passengerDtoMapper.toDto(savedPassenger));
    }

    /*@PutMapping(path = "/edit/email/{email}")
    public PassengerDtoMapper updatePassengerAccountByEmail(
            @Valid @RequestBody UpdatePassengerCommand updatePassengerAccount,
            @PathVariable String email) {
        logger.info("Editing passenger account for email: {}:", email);
        return passengerAccountService.updatePassengerAccountByEmail(updatePassengerAccount, email);
    }*/

    @GetMapping
    public ResponseEntity<PagePassengerAccountDto> retrieveAllPassengerAccountsFromDb(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PagePassengerAccountDto passengerAccounts = pagePassengerDtoMapper
                .toDto(service.retrieveAllPassengerAccountsFromDb(pageable));

        return ResponseEntity.ok(passengerAccounts);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Passenger> retrievePassengerAccountById(@PathVariable Long id) {
        logger.info("Retrieving passenger account for ID: {}:", id);
        Passenger savedPassenger = service.retrievePassengerAccountById(id);

        return ResponseEntity.ok(savedPassenger);
    }

    /*@GetMapping(path = "/get/email/{email}")
    public PassengerDtoMapper retrievePassengerAccountByEmail(@PathVariable String email) {
        logger.info("Retrieving passenger account for email: {}:", email);
        return passengerAccountService.retrievePassengerAccountByEmail(email);
    }*/

    @DeleteMapping
    public ResponseEntity<Void> deleteAllPassengerAccounts() {
        service.deleteAllPassengerAccounts();

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePassengerAccountById(@PathVariable Long id) {
        service.deletePassengerAccountById(id);

        return ResponseEntity.ok().build();
    }

    /*@DeleteMapping(path = "/delete/email/{email}")
    public void deletePassengerAccountByEmail(@PathVariable String email) {
        passengerAccountService.deletePassengerAccountByEmail(email);
    }*/

    @GetMapping(path = "/seats-booking/{email}")
    public ResponseEntity<PassengerDto> getPassengerDataFromBookingSystem(@PathVariable String email) {
        PassengerDto obtainedPassenger = passengerDtoMapper.toDto(service.getPassengerDataFromBookingSystem(email));

        return ResponseEntity.ok(obtainedPassenger);
    }
}
