package pl.flywithbookedseats.passengeraccountservice.exceptions;

import jakarta.ws.rs.NotFoundException;

public class PassengerAccountNotFoundException extends NotFoundException {
    public PassengerAccountNotFoundException(String message) {
        super((message));
    }
}
