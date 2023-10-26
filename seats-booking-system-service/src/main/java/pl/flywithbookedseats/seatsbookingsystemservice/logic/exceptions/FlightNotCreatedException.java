package pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions;

public class FlightNotCreatedException extends BadRequestException{
    public FlightNotCreatedException(String message) {
        super(message);
    }
}
