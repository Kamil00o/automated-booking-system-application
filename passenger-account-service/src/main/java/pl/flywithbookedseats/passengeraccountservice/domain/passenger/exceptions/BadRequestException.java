package pl.flywithbookedseats.passengeraccountservice.domain.passenger.exceptions;

public abstract class BadRequestException extends RuntimeException {
    protected BadRequestException(String message) {
        super(message);
    }
}
