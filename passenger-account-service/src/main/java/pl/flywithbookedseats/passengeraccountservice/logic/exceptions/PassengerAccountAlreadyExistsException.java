package pl.flywithbookedseats.passengeraccountservice.logic.exceptions;

public class PassengerAccountAlreadyExistsException extends BadRequestException {
    public PassengerAccountAlreadyExistsException(String message) {
        super(message);
    }
}
