package pl.flywithbookedseats.seatsbookingsystemservice.logic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.flight.CreateFlightCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.reservation.CreateReservationCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.seatsschememodel.CreateSeatsSchemeModelCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.flight.UpdateFlightCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.seatsschememodel.UpdateSeatsSchemeModelCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.FlightDto;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.PassengerDto;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.ReservationDto;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.SeatsSchemeModelDto;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.flight.FlightServiceImpl;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.passenger.PassengerServiceImpl;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.reservation.ReservationServiceImpl;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.seatsschememodel.SeatsBookingSystemServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/seats-booking")
public class SeatsBookingSystemController {

    private static final Logger logger = LoggerFactory.getLogger(SeatsBookingSystemController.class);
    private final SeatsBookingSystemServiceImpl seatsBookingSystemServiceImpl;
    private final FlightServiceImpl flightService;
    private final PassengerServiceImpl passengerService;
    private final ReservationServiceImpl reservationService;

    @GetMapping(path = "/test")
    public String test() {
        return "TEST";
    }

    //Methods related with reservation domain:

    @PostMapping(path = "/add-reservation")
    public ReservationDto addNewReservationToDb(@Valid @RequestBody CreateReservationCommand createReservationCommand) {
        logger.info("Adding new reservation for {} flight to database", createReservationCommand.flightNumber());
        return reservationService.addNewReservationToDb(createReservationCommand);
    }

    //Methods related with flight domain:

    @GetMapping(path = "/get-flight/all")
    public List<FlightDto> retrieveAllFlightsFromDb() {
        logger.info("Retrieving all available flights from database:");
        return flightService.retrieveAllFlightsFromDb();
    }

    @GetMapping(path = "/get-flight/flight-name/{flightName}")
    public FlightDto retrieveFlightByFlightName(@PathVariable String flightName) {
        logger.info("Retrieving flight with flight name {}:", flightName);
        return flightService.retrieveFlightByFlightName(flightName);
    }

    @GetMapping(path = "/get-flight/flight-service-id/{id}")
    public FlightDto retrieveFlightByFlightServiceId(@PathVariable Long id) {
        logger.info("Retrieving flight with flight-service ID {}:", id);
        return flightService.retrieveFlightByFlightServiceId(id);
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

    @PutMapping(path = "/update-flight/flight-service-id/{id}")
    public FlightDto updateFlightByFlightServiceId(UpdateFlightCommand updateFlightCommand, Long flightServiceId) {
        return flightService.updateFlightByFlightServiceId(updateFlightCommand, flightServiceId);
    }

    //Methods related with seats scheme Model domain:

    @PostMapping(path = "/add-new-seats-model")
    public void addNewSeatsSchemeModel(@Valid @RequestBody CreateSeatsSchemeModelCommand createSeatsSchemeModelCommand) {
        String planeModelName = createSeatsSchemeModelCommand.planeModelName();
        logger.info("Adding new seats scheme to database for {} plane model.", planeModelName);
        seatsBookingSystemServiceImpl.addNewSeatsSchemeModel(createSeatsSchemeModelCommand);
        logger.info("Seats scheme for {} added succesfully!!", planeModelName);
    }

    @GetMapping(path = "/get-seats-model/plane-model-name/{planeModelName}")
    public SeatsSchemeModelDto retrieveSeatsSchemeModelByPlaneModel(@PathVariable String planeModelName) {
        logger.info("Retrieving seat scheme model data for {} plane model.", planeModelName);
        return seatsBookingSystemServiceImpl.retrieveSeatsSchemeModelByPlaneModel(planeModelName);
    }

    @GetMapping(path = "/get-seats-model/id/{id}")
    public SeatsSchemeModelDto retrieveSeatsSchemeModelById(@PathVariable Long id) {
        logger.info("Retrieving seat scheme model data for plane model with id {}.", id);
        return seatsBookingSystemServiceImpl.retrieveSeatsSchemeModelById(id);
    }

    @GetMapping(path = "/get-seats-model/all")
    public List<SeatsSchemeModelDto> retrieveAllSavedSeatsSchemeModelsFromDb() {
        logger.info("Retrieving seat scheme model data for all saved plane models.");
        return seatsBookingSystemServiceImpl.retrieveAllSavedSeatsSchemeModelsFromDb();
    }

    @PutMapping(path = "/update-seats-scheme-model/{id}")
    public SeatsSchemeModelDto updateSeatsSchemeModel(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSeatsSchemeModelCommand updateSeatsSchemeModelCommand) {
        logger.info("Updating seat scheme model data with ID: {}.", id);
        return seatsBookingSystemServiceImpl.updateSeatsSchemeModel(id, updateSeatsSchemeModelCommand);
    }

    @DeleteMapping(path = "/delete-seats-scheme-model/id/{id}")
    public void deleteSeatsSchemeModelById(@PathVariable Long id) {
        seatsBookingSystemServiceImpl.deleteSeatsSchemeModelById(id);
    }

    @DeleteMapping(path = "/delete-seats-scheme-model/plane-name/{planeModelName}")
    public void deleteSeatsSchemeModelByPlaneModelName(@PathVariable String planeModelName) {
        seatsBookingSystemServiceImpl.deleteSeatsSchemeModelByPlaneModelName(planeModelName);
    }

    @DeleteMapping(path = "/delete-all-seats-scheme-models")
    public void deleteAllSavedSeatsSchemeModelsFromDb() {
        seatsBookingSystemServiceImpl.deleteAllSavedSeatsSchemeModelsFromDb();
    }

    //Methods related with passenger domain:

    @GetMapping(path = "get-passenger/email/{email}")
    public PassengerDto retrievePassengerByEmail(@PathVariable String email) {
        logger.info("Retrieving passenger data for email {}:", email);
        return passengerService.retrievePassengerByEmail(email);
    }
}
