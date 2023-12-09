package pl.flywithbookedseats.domain.passenger;

import pl.flywithbookedseats.external.message.passenger.RequestType;

public interface ProducerService {

    void sendPassengerUpdateMessage(Passenger passenger, RequestType requestType);
}
