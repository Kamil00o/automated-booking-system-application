package pl.flywithbookedseats.api.passenger;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.appservices.PassengerApplicationService;
import pl.flywithbookedseats.domain.passenger.Passenger;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/passengers")
public class PassengerController {

    private static final Logger logger = LoggerFactory.getLogger(PassengerController.class);

    private final PassengerApplicationService service;
    private final PassengerDtoMapper passengerDtoMapper;
    private final CreatePassengerCommandMapper createPassengerCommandMapper;
    private final UpdatePassengerCommandMapper updatePassengerCommandMapper;
    private final PagePassengerDtoMapper pagePassengerDtoMapper;

    @GetMapping(path = "/test")
    public String getTestString() {
        return "test string";
    }

    @PostMapping
    public ResponseEntity<PassengerDto> createNewPassengerAccount(
            @Valid @RequestBody CreatePassengerCommand createPassengerCommand) {
        Passenger passenger = service.
                createNewPassengerAccount(createPassengerCommandMapper.toDomain(createPassengerCommand));

        return ResponseEntity.ok(passengerDtoMapper.toDto(passenger));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PassengerDto> updatePassengerAccountById(
            @PathVariable long id,
            @Valid @RequestBody UpdatePassengerCommand updatePassengerCommand) {
        logger.info("Editing passenger account for ID: {}:", id);
        Passenger savedPassenger = service
                .updatePassengerAccountById(id, updatePassengerCommandMapper.toDomain(updatePassengerCommand));

        return ResponseEntity.ok(passengerDtoMapper.toDto(savedPassenger));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN'")
    public ResponseEntity<PagePassengerAccountDto> retrieveAllPassengerAccountsFromDb(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PagePassengerAccountDto passengerAccounts = pagePassengerDtoMapper
                .toDto(service.retrieveAllPassengerAccountsFromDb(pageable));

        return ResponseEntity.ok(passengerAccounts);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PassengerDto> retrievePassengerAccountById(@PathVariable Long id) {
        logger.info("Retrieving passenger account for ID: {}:", id);
        Passenger savedPassenger = service.retrievePassengerAccountById(id);

        return ResponseEntity.ok(passengerDtoMapper.toDto(savedPassenger));
    }

    @GetMapping(path = "/email/{email}")
    public ResponseEntity<PassengerDto> retrievePassengerAccountByEmail(@PathVariable String email) {
        logger.info("Retrieving passenger account for email: {}:", email);
        Passenger savedPassenger = service.retrievePassengerAccountByEmail(email);

        return ResponseEntity.ok(passengerDtoMapper.toDto(savedPassenger));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN'")
    public ResponseEntity<Void> deleteAllPassengerAccounts() {
        service.deleteAllPassengerAccounts();

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePassengerAccountById(@PathVariable Long id) {
        service.deletePassengerAccountById(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/seats-booking/{email}")
    @PreAuthorize("hasRole('ADMIN'")
    public ResponseEntity<PassengerDto> getPassengerDataFromBookingSystem(@PathVariable String email) {
        PassengerDto obtainedPassenger = passengerDtoMapper.toDto(service.getPassengerDataFromBookingSystem(email));

        return ResponseEntity.ok(obtainedPassenger);
    }

    @PostMapping(path = "/send-update-msg")
    @PreAuthorize("hasRole('ADMIN'")
    public ResponseEntity<PassengerDto> sendPassnegerMessageToBookingService(
            @Valid @RequestBody CreatePassengerCommand createPassengerCommand) {
        service.sendRequestedPassengerEvent(createPassengerCommandMapper.toDomain(createPassengerCommand));
        return ResponseEntity.ok().build();
    }
}
