package pl.flywithbookedseats.logic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.kafka.BookingServiceProducer;
import pl.flywithbookedseats.kafka.EventsFactory;
import pl.flywithbookedseats.logic.model.command.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.logic.model.command.passenger.UpdatePassengerCommand;
import pl.flywithbookedseats.api.reservation.CreateReservationCommand;
import pl.flywithbookedseats.api.reservation.UpdateReservationCommand;
import pl.flywithbookedseats.logic.model.dto.PassengerDto;
import pl.flywithbookedseats.api.reservation.ReservationDto;
import pl.flywithbookedseats.domain.flight.FlightService1Impl;
import pl.flywithbookedseats.logic.service.implementation.passenger.PassengerServiceImpl;
import pl.flywithbookedseats.domain.reservation.ReservationServiceImpl;
import pl.flywithbookedseats.domain.seatsbookingsystem.SeatsBookingService;

import java.util.List;

import static pl.flywithbookedseats.domain.reservation.ReservationConstsImpl.*;

@RestController
@RequiredArgsConstructor
@EnableFeignClients
@RequestMapping(path = "/seats-booking")
public class SeatsBookingSystemControllerOld {

    private static final Logger logger = LoggerFactory.getLogger(SeatsBookingSystemControllerOld.class);
    private final FlightService1Impl flightService;
    private final PassengerServiceImpl passengerService;
    private final ReservationServiceImpl reservationService;
    private final SeatsBookingService bookingService;
    private final BookingServiceProducer bookingServiceProducer;

    @GetMapping(path = "/test")
    public String test() {
        return "TEST";
    }

    //Methods related with booking process:

    /*@PostMapping(path = "/book-seats")
    public ReservationDto bookSeatsInThePlane(@Valid @RequestBody BookingEnterDataCommand bookingEnterDataCommand) {
        return bookingService.bookSeatsInThePlane(bookingEnterDataCommand);
    }

    @DeleteMapping(path = "/delete-booking/reservationId/{reservationId}")
    public void deleteBookedReservationAndAssociatedData(@PathVariable Long reservationId) {
        bookingService.deleteBookedReservationAndAssociatedData(reservationId);
        logger.info("ReservationEntity and its data have been removed.");
    }*/

    //Methods related with reservation domain:

    @PostMapping(path = "/add-reservation")
    public ReservationDto addNewReservationToDb(@Valid @RequestBody CreateReservationCommand createReservationCommand) {
        logger.info("Adding new reservation for {} flight to database", createReservationCommand.flightNumber());
        return reservationService.addNewReservationToDb(createReservationCommand);
    }

    @PutMapping(path = "/update-reservation/id/{id}")
    public ReservationDto updateReservationById(@Valid @RequestBody UpdateReservationCommand updateReservationCommand,
                                                @PathVariable Long id) {
        return reservationService.updateReservationById(updateReservationCommand, id);
    }

    @GetMapping(path = "/get-reservation/all")
    public List<ReservationDto> retrieveAllReservations() {
        return reservationService.retrieveAllReservations();
    }

    @GetMapping(path = "/get-reservation/id/{id}")
    public ReservationDto retrieveReservationById(@PathVariable Long id) {
        logger.info("Retrieving reservation for ID: {}:", id);
        return reservationService.retrieveReservationById(id);
    }

    @GetMapping(path = "/get-reservation/email/{email}")
    public List<ReservationDto> retrieveReservationByEmail(@PathVariable String email) {
        logger.info("Retrieving reservation for email: {}:", email);
        return reservationService.retrieveReservationByEmail(email);
    }

    @DeleteMapping(path = "delete-reservation/all")
    public void deleteAllReservations() {
        logger.info("Removing all reservations:");
        reservationService.deleteAllReservations();
        logger.info(REMOVING_RESERVATION_COMPLETE);
    }

    @DeleteMapping(path = "delete-reservation/id/{id}")
    public void deleteReservationById(@PathVariable Long id) {
        logger.info("Removing reservation for ID: {}:", id);
        reservationService.deleteReservationById(id);
        logger.info(REMOVING_RESERVATION_COMPLETE);
    }

    //Methods related with flight domain:

    /*@GetMapping(path = "/get-flight/all")
    public List<FlightDto> retrieveAllFlightsFromDb() {
        logger.info("Retrieving all available flights from database:");
        return flightService.retrieveAllFlightsFromDb();
    }

    @GetMapping(path = "/get-flight/flight-name/{flightName}")
    public FlightDto retrieveFlightByFlightName(@PathVariable String flightName) {
        logger.info("Retrieving flight with flight name {}:", flightName);
        return flightService.retrieveFlightByFlightName(flightName);
    }

    @GetMapping(path = "/get-flight/flight-service-id/{flightServiceId}")
    public FlightDto retrieveFlightByFlightServiceId(@PathVariable Long flightServiceId) {
        logger.info("Retrieving flight with flight-service ID {}:", flightServiceId);
        return flightService.retrieveFlightByFlightServiceId(flightServiceId);
    }

    @PostMapping(path = "/create-flight")
    public FlightDto createNewFlight(@Valid @RequestBody CreateFlightCommand createFlightCommand) {
        return flightService.createNewFlight(createFlightCommand);
    }

    @PutMapping(path = "/update-flight/flight-name/{flightName}")
    public FlightDto updateFlightByFlightName(@Valid @RequestBody UpdateFlightCommand updateFlightCommand,
                                           @PathVariable String flightName) {
        return flightService.updateFlightByFlightName(updateFlightCommand, flightName);
    }

    @PutMapping(path = "/update-flight/flight-service-id/{flightServiceId}")
    public FlightDto updateFlightByFlightServiceId(@Valid @RequestBody UpdateFlightCommand updateFlightCommand,
                                                   @PathVariable Long flightServiceId) {
        return flightService.updateFlightByFlightServiceId(updateFlightCommand, flightServiceId);
    }

    @DeleteMapping(path = "/delete-flight/all")
    public void deleteAllFlights() {
        logger.info("Removing all flights:");
        flightService.deleteAllFlights();
    }

    @DeleteMapping(path = "/delete-flight/flight-name/{flightName}")
    public void deleteFlightByFlightName(@PathVariable String flightName) {
        logger.info("Removing {} flight:", flightName);
        flightService.deleteFlightByFlightName(flightName);
    }

    @DeleteMapping(path = "/delete-flight/flight-service-id/{flightServiceId}")
    public void deleteFlightByFlyServiceId(@PathVariable Long flightServiceId) {
        logger.info("Removing flight with flight service ID: {}:", flightServiceId);
        flightService.deleteFlightByFlyServiceId(flightServiceId);
    }

    @GetMapping(path = "/flight-test/find-seat")
    public String testFindSeatForPassengerMethod() {
        return flightService.testBookSeatInFlightSeatsScheme();
    }*/

    //Methods related with passenger domain:

    @PostMapping(path = "/add-new-passenger")
    public PassengerDto createNewPassenger(@Valid @RequestBody CreatePassengerCommand createPassengerCommand) {
        logger.info("Creating new passenger for email: {}:", createPassengerCommand.email());
        return passengerService.createNewPassenger(createPassengerCommand);
    }

    @PutMapping(path = "/update-passenger/email/{email}")
    public PassengerDto updatePassengerByEmail(@Valid @RequestBody UpdatePassengerCommand updatePassengerCommand,
                                               @PathVariable String email) {
        logger.info("Updating passenger data for email: {}: ", email);
        return passengerService.updatePassengerByEmail(updatePassengerCommand, email);
    }

    @GetMapping(path = "get-passenger/email/{email}")
    public PassengerDto retrievePassengerByEmail(@PathVariable String email) {
        logger.info("Retrieving passenger data for email {}:", email);
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
