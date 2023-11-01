package pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions;

public class ReservationNotFoundException extends BadRequestException{
    public ReservationNotFoundException(String message) {
        super(message);
    }
}
