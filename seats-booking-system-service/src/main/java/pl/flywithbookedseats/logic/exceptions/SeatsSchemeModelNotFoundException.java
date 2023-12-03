package pl.flywithbookedseats.logic.exceptions;

public class SeatsSchemeModelNotFoundException extends BadRequestException{
    public SeatsSchemeModelNotFoundException(String message) {
        super(message);
    }
}
