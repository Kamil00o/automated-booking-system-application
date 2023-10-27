package pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions;

public class FlightNotFoundException extends BadRequestException{
    public FlightNotFoundException(String message) {
        super(message);
    }
}
