package pl.flywithbookedseats.domain.flight;

public class FlightDatabaseIsEmptyException extends RuntimeException {
    public FlightDatabaseIsEmptyException(String message) {
        super(message);
    }
}
