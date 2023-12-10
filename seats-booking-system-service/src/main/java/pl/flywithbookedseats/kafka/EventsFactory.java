package pl.flywithbookedseats.kafka;

import pl.flywithbookedseats.logic.model.dto.PassengerDto;

public class EventsFactory {

    private static final String MESSAGE = "Update Passenger Account for %s email";
    private static final String MESSAGE_DATA_REQUEST = "Data request for Passenger Account for %s email";
    private static final String MESSAGE_SOURCE = "seats-booking-system-service";
    private static final String STATUS = "PassengerDto status is in pending state";

    public static PassengerDtoEvent createPassengerDtoEvent(RequestType requestType, PassengerDto passengerDto) {
        return new PassengerDtoEvent(MESSAGE_DATA_REQUEST.formatted(passengerDto.email()),
                STATUS, requestType, passengerDto);
    }

    public static UpdatedPassengerEvent createUpdatedPassengerEvent(PassengerDto passengerDto) {
        return new UpdatedPassengerEvent(MESSAGE.formatted(passengerDto.email()), MESSAGE_SOURCE, STATUS, passengerDto);
    }
}
