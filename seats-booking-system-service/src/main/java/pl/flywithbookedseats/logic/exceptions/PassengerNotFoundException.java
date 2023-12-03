package pl.flywithbookedseats.logic.exceptions;

public class PassengerNotFoundException extends BadRequestException{
    public PassengerNotFoundException(String message) {
        super(message);
    }
}
