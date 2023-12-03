package pl.flywithbookedseats.logic.exceptions;

public class FullFlightException extends BadRequestException{
    public FullFlightException(String message) {
        super(message);
    }
}
