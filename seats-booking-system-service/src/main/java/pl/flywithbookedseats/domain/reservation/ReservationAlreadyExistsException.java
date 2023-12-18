package pl.flywithbookedseats.domain.reservation;

public class ReservationAlreadyExistsException extends RuntimeException {
    public ReservationAlreadyExistsException(String message) {
        super(message);
    }
}
