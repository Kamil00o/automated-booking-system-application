package pl.flywithbookedseats.kafka;

import org.springframework.beans.factory.annotation.Value;
import pl.flywithbookedseats.logic.model.dto.PassengerDto;

public class EventsFactory {

    private static final String MESSAGE = "Update Passenger Account for %s email";
    @Value("${spring.application.name}")
    private static String messageSource;
    private static final String STATUS = "PassengerDto status is in pending state";

    public static PassengerDtoEvent createPassengerDtoEvent(RequestType requestType, PassengerDto passengerDto) {
        return new PassengerDtoEvent(MESSAGE, STATUS, requestType, passengerDto);
    }

    public static UpdatedPassengerEvent createUpdatedPassengerEvent(PassengerDto passengerDto) {
        return new UpdatedPassengerEvent(MESSAGE.formatted(passengerDto.email()), messageSource, STATUS, passengerDto);
    }
}
