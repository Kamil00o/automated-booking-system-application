package pl.flywithbookedseats.passengeraccountservice.domain.passenger;

public class PassengerNotFoundException extends RuntimeException {
    public PassengerNotFoundException(String message) {
        super((message));
    }
}
