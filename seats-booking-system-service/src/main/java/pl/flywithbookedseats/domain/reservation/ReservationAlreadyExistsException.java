package pl.flywithbookedseats.domain.reservation;

import pl.flywithbookedseats.logic.exceptions.BadRequestException;

public class ReservationAlreadyExistsException extends BadRequestException {
    public ReservationAlreadyExistsException(String message) {
        super(message);
    }
}
