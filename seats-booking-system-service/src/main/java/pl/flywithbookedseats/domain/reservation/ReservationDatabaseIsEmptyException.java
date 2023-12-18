package pl.flywithbookedseats.domain.reservation;

public class ReservationDatabaseIsEmptyException extends RuntimeException {
    public ReservationDatabaseIsEmptyException(String message) {
        super(message);
    }
}
