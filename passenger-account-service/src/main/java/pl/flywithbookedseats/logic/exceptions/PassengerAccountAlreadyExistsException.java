package pl.flywithbookedseats.logic.exceptions;

public class PassengerAccountAlreadyExistsException extends BadRequestException {
    public PassengerAccountAlreadyExistsException(String message) {
        super(message);
    }
}
