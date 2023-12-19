package pl.flywithbookedseats.api.passenger;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
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
    private final PassengerService service;

    @PostMapping(path = "/add-new-passenger")
    public PassengerDto createNewPassenger(@Valid @RequestBody CreatePassengerCommand createPassengerCommand) {
        log.info("Creating new passenger for email: {}:", createPassengerCommand.email());
        return passengerService.createNewPassenger(createPassengerCommand);
    }

    @PutMapping(path = "/update-passenger/email/{email}")
    public PassengerDto updatePassengerByEmail(@Valid @RequestBody UpdatePassengerCommand updatePassengerCommand,
                                               @PathVariable String email) {
        log.info("Updating passenger data for email: {}: ", email);
        return passengerService.updatePassengerByEmail(updatePassengerCommand, email);
    }

    @GetMapping(path = "get-passenger/email/{email}")
    public PassengerDto retrievePassengerByEmail(@PathVariable String email) {
        log.info("Retrieving passenger data for email {}:", email);
        return passengerService.retrievePassengerByEmail(email);
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
