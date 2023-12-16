package pl.flywithbookedseats.domain.flight;

import pl.flywithbookedseats.logic.exceptions.BadRequestException;

public class FlightNotCreatedException extends BadRequestException {
    public FlightNotCreatedException(String message) {
        super(message);
    }
}
