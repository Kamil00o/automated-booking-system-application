package pl.flywithbookedseats.domain.reservation;

import pl.flywithbookedseats.logic.exceptions.BadRequestException;

public class ReservationDatabaseIsEmptyException extends BadRequestException {
    public ReservationDatabaseIsEmptyException(String message) {
        super(message);
    }
}
