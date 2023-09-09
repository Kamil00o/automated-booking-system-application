package pl.flywithbookedseats.passengeraccountservice.exceptions;

public class PassengerAccountNotFoundException extends RuntimeException {
    public PassengerAccountNotFoundException(String message) {
        super((message));
    }
}
