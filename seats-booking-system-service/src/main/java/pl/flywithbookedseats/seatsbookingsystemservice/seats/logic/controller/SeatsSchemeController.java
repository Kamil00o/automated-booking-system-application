package pl.flywithbookedseats.seatsbookingsystemservice.seats.logic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.seatsbookingsystemservice.seats.logic.model.command.CreateSeatsScheme;
import pl.flywithbookedseats.seatsbookingsystemservice.seats.logic.model.domain.SeatsScheme;
import pl.flywithbookedseats.seatsbookingsystemservice.seats.logic.service.SeatsSchemeService;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/seats-scheme")
public class SeatsSchemeController {

    private final SeatsSchemeService seatsSchemeService;

    @GetMapping(path = "/test")
    public String test() {
        return "test";
    }
    
    @GetMapping(path = "/all")
    public List<SeatsScheme> retrieveAllSeatsSchemes() {
        return new LinkedList<>();
    }

    @PostMapping(path = "/create")
    public SeatsScheme createNewSeatsScheme(@Valid @RequestBody CreateSeatsScheme createSeatsScheme) {
        return new SeatsScheme();
    }

    @GetMapping(path = "/get/by-plane-model/{planeModel}")
    public SeatsScheme retireveSeatsSchemeByPlaneModel(@PathVariable String planeModel) {
        return new SeatsScheme();
    }
}
