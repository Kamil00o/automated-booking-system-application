package pl.flywithbookedseats.domain.flight;

public class FullFlightException extends RuntimeException {
    public FullFlightException(String message) {
        super(message);
    }
}
