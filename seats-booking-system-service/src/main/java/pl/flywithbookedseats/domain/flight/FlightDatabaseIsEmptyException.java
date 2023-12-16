package pl.flywithbookedseats.domain.flight;

import pl.flywithbookedseats.logic.exceptions.BadRequestException;

public class FlightDatabaseIsEmptyException extends BadRequestException {
    public FlightDatabaseIsEmptyException(String message) {
        super(message);
    }
}
