package pl.flywithbookedseats.logic.exceptions;

public class SeatsSchemeModelAlreadyExistsException extends BadRequestException{
    public SeatsSchemeModelAlreadyExistsException(String message) {
        super(message);
    }
}
