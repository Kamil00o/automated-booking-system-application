package pl.flywithbookedseats.domain.reservation;

import pl.flywithbookedseats.logic.exceptions.BadRequestException;

public class ReservationNotFoundException extends BadRequestException {
    public ReservationNotFoundException(String message) {
        super(message);
    }
}
