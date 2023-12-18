package pl.flywithbookedseats.domain.passenger;

public class PassengerAlreadyExistsException extends BadRequestException{
    public PassengerAlreadyExistsException(String message) {
        super(message);
    }
}
