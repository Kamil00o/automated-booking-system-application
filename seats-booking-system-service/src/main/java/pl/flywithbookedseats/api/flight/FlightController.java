package pl.flywithbookedseats.api.flight;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.appservices.FlightApplicationService;
import pl.flywithbookedseats.domain.flight.Flight;
import pl.flywithbookedseats.domain.flight.FlightService1Impl;
import pl.flywithbookedseats.domain.flight.PageFlight;

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
    private final PageFlightDtoMapper pageMapper;

    @PostMapping
    public FlightDto createNewFlight(@Valid @RequestBody CreateFlightCommand createFlightCommand) {
        Flight createdFlight = service.createNewFlight(createMapper.toDomain(createFlightCommand));

        return mapper.toDto(createdFlight, service
                        .convertBookedSeatsInPlaneMapToDtoVersion(createdFlight.getBookedSeatsInPlaneMap()));
    }

    @GetMapping
    public PageFlightDto retrieveAllFlightsFromDb(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size) {
        log.info("Retrieving all available flights from database:");
        Pageable pageable = PageRequest.of(page, size);
        PageFlight savedPageFlight = service.retrieveAllFlightsFromDb(pageable);

        return pageMapper.toDto(savedPageFlight);
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

    @DeleteMapping(path = "/all")
    public void deleteAllFlights() {
        log.info("Removing all flights:");
        service.deleteAllFlights();
    }

    @DeleteMapping(path = "/flightName/{flightName}")
    public void deleteFlightByFlightName(@PathVariable String flightName) {
        log.info("Removing {} flight:", flightName);
        service.deleteFlightByFlightName(flightName);
    }

    @DeleteMapping(path = "/flightServiceId/{flightServiceId}")
    public void deleteFlightByFlyServiceId(@PathVariable Long flightServiceId) {
        log.info("Removing flight with flight service ID: {}:", flightServiceId);
        service.deleteFlightByFlyServiceId(flightServiceId);
    }

    @GetMapping(path = "/flight-test/find-seat")
    public String testFindSeatForPassengerMethod() {
        return flightService.testBookSeatInFlightSeatsScheme();
    }
}
