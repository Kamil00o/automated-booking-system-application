package pl.flywithbookedseats.domain.flight;

import pl.flywithbookedseats.logic.exceptions.BadRequestException;

public class FlightAlreadyExistsException extends BadRequestException {
    public FlightAlreadyExistsException(String message) {
        super(message);
    }
}
