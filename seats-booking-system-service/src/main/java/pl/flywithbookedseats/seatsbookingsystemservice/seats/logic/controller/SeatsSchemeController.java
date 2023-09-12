package pl.flywithbookedseats.seatsbookingsystemservice.seats.logic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.flywithbookedseats.seatsbookingsystemservice.seats.logic.service.SeatsSchemeService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/seats-scheme")
public class SeatsSchemeController {

    private final SeatsSchemeService seatsSchemeService;

    @GetMapping(path = "/test")
    public String test() {
        return "test";
    }
}
