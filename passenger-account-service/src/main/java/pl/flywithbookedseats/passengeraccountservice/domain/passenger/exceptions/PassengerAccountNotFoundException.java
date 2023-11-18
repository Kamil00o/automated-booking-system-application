package pl.flywithbookedseats.passengeraccountservice.domain.passenger.exceptions;

import jakarta.ws.rs.NotFoundException;

public class PassengerAccountNotFoundException extends NotFoundException {
    public PassengerAccountNotFoundException(String message) {
        super((message));
    }
}
