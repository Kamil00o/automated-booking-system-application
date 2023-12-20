package pl.flywithbookedseats.api.passenger;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.appservices.PassengerApplicationService;
import pl.flywithbookedseats.domain.passenger.PagePassenger;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.external.message.passenger.BookingServiceProducer;
import pl.flywithbookedseats.external.message.passenger.EventsFactory;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/seats-booking/passenger")
public class PassengerController {

    private final BookingServiceProducer bookingServiceProducer;
    private final PassengerApplicationService service;
    private final CreatePassengerCommandMapper createMapper;
    private final PassengerDtoMapper mapper;
    private final UpdatePassengerCommandMapper updateMapper;
    private final PagePassengerDtoMapper pageMapper;

    @PostMapping
    public ResponseEntity<PassengerDto> createNewPassenger(@Valid @RequestBody CreatePassengerCommand createPassengerCommand) {
        log.info("Creating new passenger for email: {}:", createPassengerCommand.email());
        Passenger createdPassenger = service.createNewPassenger(createMapper.toDomain(createPassengerCommand));

        return ResponseEntity.ok(mapper.toDto(createdPassenger));
    }

    @PutMapping(path = "/email/{email}")
    public ResponseEntity<PassengerDto> updatePassengerByEmail(@Valid @RequestBody UpdatePassengerCommand updatePassengerCommand,
                                               @PathVariable String email) {
        log.info("Updating passenger data for email: {}: ", email);
        Passenger updatedPassenger = service
                .updatePassengerByEmail(updateMapper.toDomain(updatePassengerCommand), email);

        return ResponseEntity.ok(mapper.toDto(updatedPassenger));
    }

    @PutMapping(path = "/id/{id}")
    public ResponseEntity<PassengerDto> updatePassengerById(@Valid @RequestBody UpdatePassengerCommand updatePassengerCommand,
                                               @PathVariable Long id) {
        log.info("Updating passenger data for ID: {}: ", id);
        Passenger updatedPassenger = service
                .updatePassengerById(updateMapper.toDomain(updatePassengerCommand), id);

        return ResponseEntity.ok(mapper.toDto(updatedPassenger));
    }

    @GetMapping(path = "/email/{email}")
    public ResponseEntity<PassengerDto> retrievePassengerByEmail(@PathVariable String email) {
        log.info("Retrieving passenger data for email {}:", email);
        Passenger foundPassenger = service.retrievePassengerByEmail(email);

        return ResponseEntity.ok(mapper.toDto(foundPassenger));
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<PassengerDto> retrievePassengerById(@PathVariable Long id) {
        log.info("Retrieving passenger data for ID {}:", id);
        Passenger foundPassenger = service.retrievePassengerById(id);

        return ResponseEntity.ok(mapper.toDto(foundPassenger));
    }

    @GetMapping(path = "/passengerServiceId/{passengerServiceId}")
    public ResponseEntity<PassengerDto> retrievePassengerByPassengerServiceId(@PathVariable Long passengerServiceId) {
        log.info("Retrieving passenger data for passenger service ID {}:", passengerServiceId);
        Passenger foundPassenger = service.retrievePassengerByPassengerServiceId(passengerServiceId);

        return ResponseEntity.ok(mapper.toDto(foundPassenger));
    }

    @GetMapping
    public ResponseEntity<PagePassengerDto> retrieveAllPassengers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PagePassenger pagePassenger = service.retrieveAllPassengers(pageable);

        return ResponseEntity.ok(pageMapper.toDto(pagePassenger));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllPassengers() {
        service.deleteAllPassengers();

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<Void> deletePassengerById(@PathVariable Long id) {
        service.deletePassengerById(id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/email/{email}")
    public ResponseEntity<Void> deletePassengerByEmail(@PathVariable String email) {
        service.deletePassengerByEmail(email);

        return ResponseEntity.ok().build();
    }

    ////////////////kafka methods:

    @PostMapping(path = "/kafka/post-dto")
    public ResponseEntity<String> publishPassengerDtoFromDb(@RequestBody @Payload PassengerDto passengerDto) {
        bookingServiceProducer.sendMessage(EventsFactory.createRequestedPassengerEvent(passengerDto));
        return ResponseEntity.ok("Json message sent to kafka topic");
    }
}
