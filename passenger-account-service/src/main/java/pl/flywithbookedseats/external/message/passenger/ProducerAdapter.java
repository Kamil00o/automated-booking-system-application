package pl.flywithbookedseats.external.message.passenger;

import lombok.RequiredArgsConstructor;
import pl.flywithbookedseats.api.passenger.PassengerDtoMapper;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.ProducerService;

@RequiredArgsConstructor
public class ProducerAdapter implements ProducerService {

    private final PassengerServiceProducer producer;
    private final PassengerDtoMapper mapper;


    @Override
    public void sendPassengerUpdateMessage(Passenger passenger, RequestType requestType) {
        producer.sendMessage(EventsFactory.createPassengerDtoEvent(requestType, mapper.toDto(passenger)));
    }
}
