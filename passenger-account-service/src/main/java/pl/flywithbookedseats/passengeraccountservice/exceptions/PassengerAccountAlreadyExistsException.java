package pl.flywithbookedseats.passengeraccountservice.exceptions;

public class PassengerAccountAlreadyExistsException extends BadRequestException {
    public PassengerAccountAlreadyExistsException(String message) {
        super(message);
    }
}
