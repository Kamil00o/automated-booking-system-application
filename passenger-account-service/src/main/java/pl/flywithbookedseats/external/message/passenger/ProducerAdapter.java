package pl.flywithbookedseats.external.message.passenger;

import lombok.RequiredArgsConstructor;
import pl.flywithbookedseats.api.passenger.PassengerDtoMapper;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.ProducerService;

@RequiredArgsConstructor
public class ProducerAdapter implements ProducerService {

    private final PassengerServiceProducer producer;
    private final KafkaPassengerDtoMapper mapper;


    @Override
    public void sendRequestedPassengerEvent(Passenger passenger) {
        producer.sendMessage(EventsFactory.createRequestedPassengerEvent(mapper.toDto(passenger)));
    }

    @Override
    public void sendUpdatedPassengerEvent(Passenger passenger) {
        producer.sendUpdatedPassengerEvent(EventsFactory.createUpdatedPassengerEvent(mapper.toDto(passenger)));
    }
}
