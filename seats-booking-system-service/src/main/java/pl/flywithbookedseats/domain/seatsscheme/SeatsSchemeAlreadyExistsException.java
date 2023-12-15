package pl.flywithbookedseats.domain.seatsscheme;

public class SeatsSchemeAlreadyExistsException extends RuntimeException {
    public SeatsSchemeAlreadyExistsException(String message) {
        super(message);
    }
}
