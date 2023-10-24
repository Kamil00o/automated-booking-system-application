package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.exceptions;

public class SeatsSchemeModelAlreadyExistsException extends BadRequestException{
    protected SeatsSchemeModelAlreadyExistsException(String message) {
        super(message);
    }
}
