package pl.flywithbookedseats.external.message.passenger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.ProducerService;

@Slf4j
@RequiredArgsConstructor
public class ProducerServiceAdapter implements ProducerService {

    private final BookingServiceProducer producer;
    private final KafkaPassengerDtoMapper mapper;

    @Override
    public void sendRequestedPassengerEvent(Passenger passenger) {
        producer.sendRequestedPassengerEvent(EventsFactory.createRequestedPassengerEvent(mapper.toDto(passenger)));
    }

    @Override
    public void sendUpdatedPassengerEvent(Passenger passenger) {
        producer.sendUpdatedPassengerEvent(EventsFactory.createUpdatedPassengerEvent(mapper.toDto(passenger)));
    }
}
