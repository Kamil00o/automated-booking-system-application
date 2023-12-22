package pl.flywithbookedseats.external.message.passenger;

public class EventsFactory {

    private static final String MESSAGE = "Update PassengerEntity Account for %s email";
    private static final String MESSAGE_DATA_REQUEST = "Data request for PassengerEntity Account for %s email";
    private static final String MESSAGE_SOURCE = "seats-booking-system-service";
    private static final String STATUS = "PassengerDto status is in pending state";

    public static RequestedPassengerEvent createRequestedPassengerEvent(KafkaPassengerDto kafkaPassengerDto) {
        return new RequestedPassengerEvent(MESSAGE_DATA_REQUEST.formatted(kafkaPassengerDto.email()),
                MESSAGE_SOURCE, STATUS, kafkaPassengerDto);
    }

    public static UpdatedPassengerEvent createUpdatedPassengerEvent(KafkaPassengerDto kafkaPassengerDto) {
        return new UpdatedPassengerEvent(MESSAGE.formatted(kafkaPassengerDto.email()),
                MESSAGE_SOURCE,
                STATUS,
                kafkaPassengerDto);
    }
}
