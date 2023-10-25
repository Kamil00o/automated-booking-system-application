package pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions;

public class SeatsSchemeModelNotFoundException extends BadRequestException{
    public SeatsSchemeModelNotFoundException(String message) {
        super(message);
    }
}
