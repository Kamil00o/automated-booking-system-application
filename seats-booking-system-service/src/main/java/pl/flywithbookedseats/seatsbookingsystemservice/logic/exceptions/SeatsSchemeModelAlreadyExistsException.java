package pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions;

public class SeatsSchemeModelAlreadyExistsException extends BadRequestException{
    public SeatsSchemeModelAlreadyExistsException(String message) {
        super(message);
    }
}
