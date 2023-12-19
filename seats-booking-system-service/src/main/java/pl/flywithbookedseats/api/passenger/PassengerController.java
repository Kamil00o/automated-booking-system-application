package pl.flywithbookedseats.api.passenger;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.appservices.PassengerApplicationService;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.PassengerService;
import pl.flywithbookedseats.domain.passenger.PassengerService1Impl;
import pl.flywithbookedseats.external.message.passenger.BookingServiceProducer;
import pl.flywithbookedseats.external.message.passenger.EventsFactory;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/seats-booking/passenger")
public class PassengerController {

    private final PassengerService1Impl passengerService;
    private final BookingServiceProducer bookingServiceProducer;
    private final PassengerApplicationService service;
    private final CreatePassengerCommandMapper createMapper;
    private final PassengerDtoMapper mapper;

    @PostMapping
    public PassengerDto createNewPassenger(@Valid @RequestBody CreatePassengerCommand createPassengerCommand) {
        log.info("Creating new passenger for email: {}:", createPassengerCommand.email());
        Passenger createdPassenger = service.createNewPassenger(createMapper.toDomain(createPassengerCommand));

        return mapper.toDto(createdPassenger);
    }

    @PutMapping(path = "/update-passenger/email/{email}")
    public PassengerDto updatePassengerByEmail(@Valid @RequestBody UpdatePassengerCommand updatePassengerCommand,
                                               @PathVariable String email) {
        log.info("Updating passenger data for email: {}: ", email);
        return passengerService.updatePassengerByEmail(updatePassengerCommand, email);
    }

    @GetMapping(path = "/email/{email}")
    public PassengerDto retrievePassengerByEmail(@PathVariable String email) {
        log.info("Retrieving passenger data for email {}:", email);
        Passenger foundPassenger = service.retrievePassengerByEmail(email);

        return mapper.toDto(foundPassenger);
    }

    @GetMapping(path = "/id/{id}")
    public PassengerDto retrievePassengerById(@PathVariable Long id) {
        log.info("Retrieving passenger data for ID {}:", id);
        Passenger foundPassenger = service.retrievePassengerById(id);

        return mapper.toDto(foundPassenger);
    }

    @GetMapping(path = "/passengerServiceId/{passengerServiceId}")
    public PassengerDto retrievePassengerByPassengerServiceId(@PathVariable Long passengerServiceId) {
        log.info("Retrieving passenger data for passenger service ID {}:", passengerServiceId);
        Passenger foundPassenger = service.retrievePassengerByPassengerServiceId(passengerServiceId);

        return mapper.toDto(foundPassenger);
    }

    @GetMapping(path = "/get-passenger/all")
    public List<PassengerDto> retrieveAllPassengers() {
        return passengerService.retrieveAllPassengers();
    }

    @DeleteMapping(path = "/delete-passenger/all")
    public void deleteAllPassengers() {
        passengerService.deleteAllPassengers();
    }

    @DeleteMapping(path = "/delete-passenger/id/{id}")
    public void deletePassengerById(@PathVariable Long id) {
        passengerService.deletePassengerById(id);
    }

    @DeleteMapping(path = "/delete-passenger/email/{email}")
    public void deletePassengerByEmail(@PathVariable String email) {
        passengerService.deletePassengerByEmail(email);
    }

    ////////////////kafka methods:

    @PostMapping(path = "/kafka/post-dto")
    public ResponseEntity<String> publishPassengerDtoFromDb(@RequestBody @Payload PassengerDto passengerDto) {
        bookingServiceProducer.sendMessage(EventsFactory.createRequestedPassengerEvent(passengerDto));
        return ResponseEntity.ok("Json message sent to kafka topic");
    }
}
