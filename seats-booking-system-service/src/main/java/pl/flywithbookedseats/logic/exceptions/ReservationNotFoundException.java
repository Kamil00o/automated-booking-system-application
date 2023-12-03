package pl.flywithbookedseats.logic.exceptions;

public class ReservationNotFoundException extends BadRequestException{
    public ReservationNotFoundException(String message) {
        super(message);
    }
}
