package pl.flywithbookedseats.domain.passenger;

public interface ProducerService {

    void sendRequestedPassengerEvent(Passenger passenger);

    void sendUpdatedPassengerEvent(Passenger passenger);

}
