package pl.flywithbookedseats.passengeraccountservice.domain.passenger.exceptions;

import jakarta.ws.rs.NotFoundException;

public class PassengerNotFoundException extends NotFoundException {
    public PassengerNotFoundException(String message) {
        super((message));
    }
}
