package pl.flywithbookedseats.domain.passenger;

public class PassengerDatabaseIsEmptyException extends RuntimeException {
    public PassengerDatabaseIsEmptyException(String message) {
        super(message);
    }
}
