package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.command.CreateFlight;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.command.UpdateFlight;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.domain.Flight;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.service.implementation.SeatsBookingSystemServiceImpl;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/seats-booking")
public class SeatsBookingSystemController {

    private final SeatsBookingSystemServiceImpl seatsBookingSystemService;

    @GetMapping(path = "/test")
    public String test() {
        return "TEST";
    }

    @GetMapping(path = "/get/all")
    public List<Flight> retrieveAllFlights() {
        return new LinkedList<>();
    }

    @GetMapping(path = "/get/id/{id}")
    public Flight retrieveFlightByFlightName(@PathVariable Long id) {
        return new Flight();
    }

    @PostMapping(path = "/create-flight")
    public Flight createNewFlight(@Valid @RequestBody CreateFlight createFlight) {
        return new Flight();
    }

    @PutMapping(path = "/edit-flight/id/{id}")
    public Flight editExistingFlight(@Valid @RequestBody UpdateFlight updateFlight, @PathVariable Long id) {
        return new Flight();
    }

    //Methods related with assigning seats & plane seats schemes to flights:

    @PostMapping(path = "/add-seats-scheme-to-flight")
    public void addSeatsSchemeToFlight() {

    }
}
