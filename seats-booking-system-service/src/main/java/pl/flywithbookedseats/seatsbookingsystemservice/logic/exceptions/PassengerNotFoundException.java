package pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions;

public class PassengerNotFoundException extends BadRequestException{
    public PassengerNotFoundException(String message) {
        super(message);
    }
}
