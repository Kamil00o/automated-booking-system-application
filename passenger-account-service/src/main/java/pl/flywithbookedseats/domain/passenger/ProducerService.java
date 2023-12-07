package pl.flywithbookedseats.domain.passenger;

public interface ProducerService {

    void sendPassengerUpdateMessage(Passenger passenger, RequestType requestType);
}
