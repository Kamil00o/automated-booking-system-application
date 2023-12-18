package pl.flywithbookedseats.domain.passenger;

public class PassengerNotFoundException extends BadRequestException{
    public PassengerNotFoundException(String message) {
        super(message);
    }
}
