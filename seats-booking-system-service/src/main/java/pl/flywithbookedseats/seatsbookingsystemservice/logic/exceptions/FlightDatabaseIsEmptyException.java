package pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions;

public class FlightDatabaseIsEmptyException extends BadRequestException{
    public FlightDatabaseIsEmptyException(String message) {
        super(message);
    }
}
