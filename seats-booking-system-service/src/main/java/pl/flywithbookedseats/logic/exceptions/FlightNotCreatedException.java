package pl.flywithbookedseats.logic.exceptions;

public class FlightNotCreatedException extends BadRequestException{
    public FlightNotCreatedException(String message) {
        super(message);
    }
}
