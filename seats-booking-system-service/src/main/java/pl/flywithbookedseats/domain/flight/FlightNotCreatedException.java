package pl.flywithbookedseats.domain.flight;

public class FlightNotCreatedException extends RuntimeException {
    public FlightNotCreatedException(String message) {
        super(message);
    }
}
