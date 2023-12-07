package pl.flywithbookedseats.external.message.passenger;


import pl.flywithbookedseats.api.passenger.PassengerDto;
import pl.flywithbookedseats.domain.passenger.RequestType;
import pl.flywithbookedseats.kafka.PassengerDtoEvent;

public class PassengerDtoEventFactory {

    private static final String message = "Pending";
    private static final String status = "PassengerDto status is in pending state";

    public static PassengerDtoEvent createPassengerDtoEvent(RequestType requestType, PassengerDto passengerDto) {
        return new PassengerDtoEvent(message, status, requestType, passengerDto);
    }
}
