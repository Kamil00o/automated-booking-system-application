package pl.flywithbookedseats.external.message.passenger;


import org.springframework.beans.factory.annotation.Value;
import pl.flywithbookedseats.api.passenger.PassengerDto;
import pl.flywithbookedseats.kafka.PassengerDtoEvent;
import pl.flywithbookedseats.kafka.UpdatedPassengerEvent;

public class EventsFactory {

    private static final String MESSAGE = "Update Passenger Account for %s email";
    private static final String MESSAGE_DATA_REQUEST = "Data request for Passenger Account for %s email";
    @Value("${spring.application.name}")
    private static String messageSource;
    private static final String STATUS = "PassengerDto status is in pending state";

    public static PassengerDtoEvent createPassengerDtoEvent(RequestType requestType, PassengerDto passengerDto) {
        return new PassengerDtoEvent(MESSAGE_DATA_REQUEST.formatted(passengerDto.email()),
                STATUS, requestType, passengerDto);
    }

    public static UpdatedPassengerEvent createUpdatedPassengerEvent(PassengerDto passengerDto) {
        return new UpdatedPassengerEvent(MESSAGE.formatted(passengerDto.email()), messageSource, STATUS, passengerDto);
    }
}
