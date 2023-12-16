package pl.flywithbookedseats.api.flight;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.appservices.FlightApplicationService;
import pl.flywithbookedseats.domain.flight.Flight;
import pl.flywithbookedseats.domain.flight.FlightService1Impl;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/seats-booking/flight")
public class FlightController {

    private final FlightService1Impl flightService;
    private final FlightApplicationService service;
    private final CreateFlightCommandMapper createMapper;
    private final FlightDtoMapper mapper;
    private final UpdateFlightCommandMapper updateMapper;

    @PostMapping
    public FlightDto createNewFlight(@Valid @RequestBody CreateFlightCommand createFlightCommand) {
        Flight createdFlight = service.createNewFlight(createMapper.toDomain(createFlightCommand));
        return mapper.toDto(createdFlight, service
                        .convertBookedSeatsInPlaneMapToDtoVersion(createdFlight.getBookedSeatsInPlaneMap()));
    }

    @GetMapping(path = "/get-flight/all")
    public List<FlightDto> retrieveAllFlightsFromDb() {
        log.info("Retrieving all available flights from database:");
        return flightService.retrieveAllFlightsFromDb();
    }

    @GetMapping(path = "/flightName/{flightName}")
    public FlightDto retrieveFlightByFlightName(@PathVariable String flightName) {
        log.info("Retrieving flight with flight name {}:", flightName);
        Flight retrievedFlight = service.retrieveFlightByFlightName(flightName);
        return mapper.toDto(retrievedFlight, service
                .convertBookedSeatsInPlaneMapToDtoVersion(retrievedFlight.getBookedSeatsInPlaneMap()));
    }

    @GetMapping(path = "/flightServiceId/{flightServiceId}")
    public FlightDto retrieveFlightByFlightServiceId(@PathVariable Long flightServiceId) {
        log.info("Retrieving flight with flight-service ID {}:", flightServiceId);
        Flight retrievedFlight = service.retrieveFlightByFlightServiceId(flightServiceId);
        return mapper.toDto(retrievedFlight, service
                .convertBookedSeatsInPlaneMapToDtoVersion(retrievedFlight.getBookedSeatsInPlaneMap()));
    }

    @PutMapping(path = "/flightName/{flightName}")
    public FlightDto updateFlightByFlightName(@Valid @RequestBody UpdateFlightCommand updateFlightCommand,
                                              @PathVariable String flightName) {
        Flight updatedFlight = service
                .updateFlightByFlightName(updateMapper.toDomain(updateFlightCommand), flightName);
        return mapper.toDto(updatedFlight, service
                .convertBookedSeatsInPlaneMapToDtoVersion(updatedFlight.getBookedSeatsInPlaneMap()));
    }

    @PutMapping(path = "/flightServiceId/{flightServiceId}")
    public FlightDto updateFlightByFlightServiceId(@Valid @RequestBody UpdateFlightCommand updateFlightCommand,
                                                   @PathVariable Long flightServiceId) {
        Flight updatedFlight = service
                .updateFlightByFlightServiceId(updateMapper.toDomain(updateFlightCommand), flightServiceId);
        return mapper.toDto(updatedFlight, service
                .convertBookedSeatsInPlaneMapToDtoVersion(updatedFlight.getBookedSeatsInPlaneMap()));
    }

    @DeleteMapping(path = "/delete-flight/all")
    public void deleteAllFlights() {
        log.info("Removing all flights:");
        flightService.deleteAllFlights();
    }

    @DeleteMapping(path = "/delete-flight/flight-name/{flightName}")
    public void deleteFlightByFlightName(@PathVariable String flightName) {
        log.info("Removing {} flight:", flightName);
        flightService.deleteFlightByFlightName(flightName);
    }

    @DeleteMapping(path = "/delete-flight/flight-service-id/{flightServiceId}")
    public void deleteFlightByFlyServiceId(@PathVariable Long flightServiceId) {
        log.info("Removing flight with flight service ID: {}:", flightServiceId);
        flightService.deleteFlightByFlyServiceId(flightServiceId);
    }

    @GetMapping(path = "/flight-test/find-seat")
    public String testFindSeatForPassengerMethod() {
        return flightService.testBookSeatInFlightSeatsScheme();
    }
}
