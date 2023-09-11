package pl.flywithbookedseats.seatsbookingsystemservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.flywithbookedseats.seatsbookingsystemservice.service.SeatsBookingSystemService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/seats-booking")
public class SeatsBookingSystemController {

    private final SeatsBookingSystemService seatsBookingSystemService;

    @GetMapping(path = "/test")
    public String test() {
        return "TEST";
    }
}
