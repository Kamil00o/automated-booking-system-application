package pl.flywithbookedseats.external.message.passenger;


import pl.flywithbookedseats.api.passenger.PassengerDto;
import pl.flywithbookedseats.kafka.RequestedPassengerEvent;
import pl.flywithbookedseats.kafka.UpdatedPassengerEvent;

public class EventsFactory {

    private static final String MESSAGE = "Update Passenger Account for %s email";
    private static final String MESSAGE_DATA_REQUEST = "Data request for Passenger Account for %s email";
    private static final String MESSAGE_SOURCE = "passenger-account-service";
    private static final String STATUS = "PassengerDto status is in pending state";

    public static RequestedPassengerEvent createRequestedPassengerEvent(PassengerDto passengerDto) {
        return new RequestedPassengerEvent(MESSAGE_DATA_REQUEST.formatted(passengerDto.email()), MESSAGE_SOURCE,
                STATUS, passengerDto);
    }

    public static UpdatedPassengerEvent createUpdatedPassengerEvent(PassengerDto passengerDto) {
        return new UpdatedPassengerEvent(MESSAGE.formatted(passengerDto.email()), MESSAGE_SOURCE, STATUS, passengerDto);
    }
}
