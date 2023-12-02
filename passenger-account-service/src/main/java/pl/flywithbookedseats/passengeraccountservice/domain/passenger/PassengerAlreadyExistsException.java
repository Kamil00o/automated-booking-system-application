package pl.flywithbookedseats.passengeraccountservice.domain.passenger;

public class PassengerAlreadyExistsException extends RuntimeException {
    public PassengerAlreadyExistsException(String message) {
        super(message);
    }
}
