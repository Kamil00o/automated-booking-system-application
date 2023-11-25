package pl.flywithbookedseats.passengeraccountservice.domain.passenger.exceptions;

public class PassengerAlreadyExistsException extends BadRequestException {
    public PassengerAlreadyExistsException(String message) {
        super(message);
    }
}
