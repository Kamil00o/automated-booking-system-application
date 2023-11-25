package pl.flywithbookedseats.passengeraccountservice.domain.passenger.exceptions;

public class PassengerNotFoundException extends BadRequestException {
    public PassengerNotFoundException(String message) {
        super((message));
    }
}
