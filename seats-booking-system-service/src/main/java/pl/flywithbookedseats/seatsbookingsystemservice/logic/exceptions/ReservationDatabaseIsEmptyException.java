package pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions;

public class ReservationDatabaseIsEmptyException extends BadRequestException{
    public ReservationDatabaseIsEmptyException(String message) {
        super(message);
    }
}
