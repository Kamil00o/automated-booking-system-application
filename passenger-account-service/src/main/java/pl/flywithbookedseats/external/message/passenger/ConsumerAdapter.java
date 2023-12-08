package pl.flywithbookedseats.external.message.passenger;

import lombok.RequiredArgsConstructor;
import pl.flywithbookedseats.api.passenger.PassengerDtoMapper;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.PassengerService;
import pl.flywithbookedseats.domain.passenger.RequestType;
import pl.flywithbookedseats.kafka.PassengerDtoEvent;

@RequiredArgsConstructor
public class ConsumerAdapter {

    private final PassengerDtoMapper mapper;
    private final PassengerService service;

    public void consumeMessage(PassengerDtoEvent passengerDtoEvent) {
        Passenger receivedPassenger = mapper.toDomain(passengerDtoEvent.getPassengerDto());
        RequestType receivedRequestType = passengerDtoEvent.getRequestType();
        if (receivedRequestType == RequestType.UPDATE) {
            service.updatePassengerAccountByEmail(receivedPassenger.getEmail(), receivedPassenger);
        } else if (receivedRequestType == RequestType.DATA_REQUEST) {
            service.handlePassengerDataRequest(receivedPassenger);
        }
    }
}
