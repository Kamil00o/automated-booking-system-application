package pl.flywithbookedseats.domain.seatsscheme;

public class SeatsSchemeNotFoundException extends RuntimeException {
    public SeatsSchemeNotFoundException(String message) {
        super(message);
    }
}
