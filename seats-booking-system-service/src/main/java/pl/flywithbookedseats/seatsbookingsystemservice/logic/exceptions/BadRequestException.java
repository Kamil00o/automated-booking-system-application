package pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions;

public abstract class BadRequestException extends RuntimeException {
    protected BadRequestException(String message) {
        super(message);
    }
}
