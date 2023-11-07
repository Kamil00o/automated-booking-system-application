package pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions;

public class FullFlightException extends BadRequestException{
    public FullFlightException(String message) {
        super(message);
    }
}
