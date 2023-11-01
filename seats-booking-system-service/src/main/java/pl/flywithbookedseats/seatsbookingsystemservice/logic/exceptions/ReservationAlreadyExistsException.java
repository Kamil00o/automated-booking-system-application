package pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions;

public class ReservationAlreadyExistsException extends BadRequestException{
    public ReservationAlreadyExistsException(String message) {
        super(message);
    }
}
