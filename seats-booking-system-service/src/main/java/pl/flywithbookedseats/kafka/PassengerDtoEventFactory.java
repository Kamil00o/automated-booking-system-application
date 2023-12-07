package pl.flywithbookedseats.kafka;

import pl.flywithbookedseats.logic.model.dto.PassengerDto;

public class PassengerDtoEventFactory {

    private static final String message = "Pending";
    private static final String status = "PassengerDto status is in pending state";

    public static PassengerDtoEvent createPassengerDtoEvent(RequestType requestType, PassengerDto passengerDto) {
        return new PassengerDtoEvent(message, status, requestType, passengerDto);
    }
}
