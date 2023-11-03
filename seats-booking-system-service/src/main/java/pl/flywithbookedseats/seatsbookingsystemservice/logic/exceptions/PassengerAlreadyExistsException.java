package pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions;

public class PassengerAlreadyExistsException extends BadRequestException{
    public PassengerAlreadyExistsException(String message) {
        super(message);
    }
}
