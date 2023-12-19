package pl.flywithbookedseats.domain.passenger;

public interface PassengerRepository {

    Passenger save(Passenger passenger);

    boolean existsByEmail(String email);

    Passenger findByEmail(String email);

    Passenger findById(Long id);

    Passenger findByPassengerServiceId(Long id);

}
