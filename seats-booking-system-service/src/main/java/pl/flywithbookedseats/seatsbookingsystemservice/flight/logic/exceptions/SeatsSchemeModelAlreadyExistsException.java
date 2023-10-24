package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.exceptions;

public class SeatsSchemeModelAlreadyExistsException extends BadRequestException{
    public SeatsSchemeModelAlreadyExistsException(String message) {
        super(message);
    }
}
