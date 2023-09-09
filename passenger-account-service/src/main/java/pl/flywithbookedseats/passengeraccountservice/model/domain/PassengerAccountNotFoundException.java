package pl.flywithbookedseats.passengeraccountservice.model.domain;

public class PassengerAccountNotFoundException extends RuntimeException {
    public PassengerAccountNotFoundException(String message) {
        super((message));
    }
}
