package pl.flywithbookedseats.external.message.passenger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.api.passenger.PassengerDtoMapper;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.PassengerService;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConsumerAdapter {

    private final KafkaPassengerDtoMapper mapper;
    private final PassengerService service;

    public void consumeRequestedPassengerEvent(RequestedPassengerEvent requestedPassengerEvent) {
        Passenger receivedPassenger = mapper.toDomain(requestedPassengerEvent.getPassengerDto());
        service.handlePassengerDataRequest(receivedPassenger);
    }

    public void consumeUpdatePassengerEvent(UpdatedPassengerEvent updatedPassengerEvent) {
        Passenger receivedPassenger = mapper.toDomain(updatedPassengerEvent.getPassengerDto());
        service.updatePassengerAccountByEmail(receivedPassenger.getEmail(), receivedPassenger);
    }
}
