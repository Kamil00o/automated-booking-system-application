package pl.flywithbookedseats.logic.exceptions;

public class PassengerDatabaseIsEmptyException extends BadRequestException{
    public PassengerDatabaseIsEmptyException(String message) {
        super(message);
    }
}