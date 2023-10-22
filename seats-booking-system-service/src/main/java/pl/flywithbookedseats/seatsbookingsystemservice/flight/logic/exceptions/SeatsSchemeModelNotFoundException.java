package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.exceptions;

public class SeatsSchemeModelNotFoundException extends BadRequestException{
    public SeatsSchemeModelNotFoundException(String message) {
        super(message);
    }
}
