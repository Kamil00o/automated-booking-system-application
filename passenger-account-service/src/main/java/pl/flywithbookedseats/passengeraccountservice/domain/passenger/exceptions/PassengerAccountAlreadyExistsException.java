package pl.flywithbookedseats.passengeraccountservice.domain.passenger.exceptions;

public class PassengerAccountAlreadyExistsException extends BadRequestException {
    public PassengerAccountAlreadyExistsException(String message) {
        super(message);
    }
}
