package pl.flywithbookedseats.domain.flight;

import pl.flywithbookedseats.logic.exceptions.BadRequestException;

public class FullFlightException extends BadRequestException {
    public FullFlightException(String message) {
        super(message);
    }
}
