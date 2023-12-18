package pl.flywithbookedseats.api.flight;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.appservices.FlightApplicationService;
import pl.flywithbookedseats.domain.flight.Flight;
import pl.flywithbookedseats.domain.flight.PageFlight;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/seats-booking/flight")
public class FlightController {

    private final FlightApplicationService service;
    private final CreateFlightCommandMapper createMapper;
    private final FlightDtoMapper mapper;
    private final UpdateFlightCommandMapper updateMapper;
    private final PageFlightDtoMapper pageMapper;

    @PostMapping
    public ResponseEntity<FlightDto> createNewFlight(@Valid @RequestBody CreateFlightCommand createFlightCommand) {
        Flight createdFlight = service.createNewFlight(createMapper.toDomain(createFlightCommand));

        return ResponseEntity.ok(mapper.toDto(createdFlight, service
                .convertBookedSeatsInPlaneMapToDtoVersion(createdFlight.getBookedSeatsInPlaneMap())));
    }

    @GetMapping
    public ResponseEntity<PageFlightDto> retrieveAllFlightsFromDb(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size) {
        log.info("Retrieving all available flights from database:");
        Pageable pageable = PageRequest.of(page, size);
        PageFlight savedPageFlight = service.retrieveAllFlightsFromDb(pageable);

        return ResponseEntity.ok(pageMapper.toDto(savedPageFlight));
    }

    @GetMapping(path = "/flightName/{flightName}")
    public ResponseEntity<FlightDto> retrieveFlightByFlightName(@PathVariable String flightName) {
        log.info("Retrieving flight with flight name {}:", flightName);
        Flight retrievedFlight = service.retrieveFlightByFlightName(flightName);

        return ResponseEntity.ok(mapper.toDto(retrievedFlight, service
                .convertBookedSeatsInPlaneMapToDtoVersion(retrievedFlight.getBookedSeatsInPlaneMap())));
    }

    @GetMapping(path = "/flightServiceId/{flightServiceId}")
    public ResponseEntity<FlightDto> retrieveFlightByFlightServiceId(@PathVariable Long flightServiceId) {
        log.info("Retrieving flight with flight-service ID {}:", flightServiceId);
        Flight retrievedFlight = service.retrieveFlightByFlightServiceId(flightServiceId);

        return ResponseEntity.ok(mapper.toDto(retrievedFlight, service
                .convertBookedSeatsInPlaneMapToDtoVersion(retrievedFlight.getBookedSeatsInPlaneMap())));
    }

    @PutMapping(path = "/flightName/{flightName}")
    public ResponseEntity<FlightDto> updateFlightByFlightName(
            @Valid @RequestBody UpdateFlightCommand updateFlightCommand,
            @PathVariable String flightName) {
        Flight updatedFlight = service
                .updateFlightByFlightName(updateMapper.toDomain(updateFlightCommand), flightName);

        return ResponseEntity.ok(mapper.toDto(updatedFlight, service
                .convertBookedSeatsInPlaneMapToDtoVersion(updatedFlight.getBookedSeatsInPlaneMap())));
    }

    @PutMapping(path = "/flightServiceId/{flightServiceId}")
    public ResponseEntity<FlightDto> updateFlightByFlightServiceId(
            @Valid @RequestBody UpdateFlightCommand updateFlightCommand,
            @PathVariable Long flightServiceId) {
        Flight updatedFlight = service
                .updateFlightByFlightServiceId(updateMapper.toDomain(updateFlightCommand), flightServiceId);

        return ResponseEntity.ok(mapper.toDto(updatedFlight, service
                .convertBookedSeatsInPlaneMapToDtoVersion(updatedFlight.getBookedSeatsInPlaneMap())));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllFlights() {
        log.info("Removing all flights:");
        service.deleteAllFlights();

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/flightName/{flightName}")
    public ResponseEntity<Void> deleteFlightByFlightName(@PathVariable String flightName) {
        log.info("Removing {} flight:", flightName);
        service.deleteFlightByFlightName(flightName);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/flightServiceId/{flightServiceId}")
    public ResponseEntity<Void> deleteFlightByFlyServiceId(@PathVariable Long flightServiceId) {
        log.info("Removing flight with flight service ID: {}:", flightServiceId);
        service.deleteFlightByFlyServiceId(flightServiceId);

        return ResponseEntity.ok().build();
    }

    /*@GetMapping(path = "/flight-test/find-seat")
    public String testFindSeatForPassengerMethod() {
        return flightService.testBookSeatInFlightSeatsScheme();
    }*/
}
