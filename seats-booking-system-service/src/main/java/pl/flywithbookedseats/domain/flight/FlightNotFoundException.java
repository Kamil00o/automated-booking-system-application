package pl.flywithbookedseats.domain.flight;

import pl.flywithbookedseats.logic.exceptions.BadRequestException;

public class FlightNotFoundException extends BadRequestException {
    public FlightNotFoundException(String message) {
        super(message);
    }
}
