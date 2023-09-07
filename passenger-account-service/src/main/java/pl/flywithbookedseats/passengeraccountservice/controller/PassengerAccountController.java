package pl.flywithbookedseats.passengeraccountservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/passenger-account")
public class PassengerAccountController {

    @GetMapping(path = "/test")
    public String getTestString() {
        return "test string";
    }
}
