package pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions;

public class FlightAlreadyExistsException extends BadRequestException{
    public FlightAlreadyExistsException(String message) {
        super(message);
    }
}
