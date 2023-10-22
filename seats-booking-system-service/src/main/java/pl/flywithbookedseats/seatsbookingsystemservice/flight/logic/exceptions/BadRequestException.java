package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.exceptions;

public abstract class BadRequestException extends RuntimeException {
    protected BadRequestException(String message) {
        super(message);
    }
}
