package pl.flywithbookedseats.domain.passenger;

import pl.flywithbookedseats.external.message.passenger.RequestType;

public interface ProducerService {

    void sendRequestedPassengerEvent(Passenger passenger, RequestType requestType);

    void sendUpdatedPassengerEvent(Passenger passenger);
}
