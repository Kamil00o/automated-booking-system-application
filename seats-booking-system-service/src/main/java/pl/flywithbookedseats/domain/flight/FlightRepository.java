package pl.flywithbookedseats.domain.flight;

public interface FlightRepository {

    boolean existsByFlightName(String flightName);

    Flight save(Flight flight);

    Flight findByFlightName(String flightName);

    Flight findByFlightServiceId(Long flightServiceId);

}
