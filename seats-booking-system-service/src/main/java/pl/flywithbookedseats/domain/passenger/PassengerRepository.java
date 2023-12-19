package pl.flywithbookedseats.domain.passenger;

public interface PassengerRepository {

    Passenger save(Passenger passenger);

    boolean existsByEmail(String email);

}
