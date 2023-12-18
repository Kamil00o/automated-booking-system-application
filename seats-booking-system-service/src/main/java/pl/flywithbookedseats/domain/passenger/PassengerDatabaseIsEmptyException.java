package pl.flywithbookedseats.domain.passenger;

public class PassengerDatabaseIsEmptyException extends BadRequestException{
    public PassengerDatabaseIsEmptyException(String message) {
        super(message);
    }
}
