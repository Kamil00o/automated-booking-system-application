package pl.flywithbookedseats.domain.flight;

import org.springframework.data.domain.Pageable;

public interface FlightRepository {

    boolean existsByFlightName(String flightName);

    Flight save(Flight flight);

    Flight findByFlightName(String flightName);

    Flight findByFlightServiceId(Long flightServiceId);

    void deleteByFlightServiceId(Long flightServiceId);

    void deleteByFlightName(String flightName);

    void deleteAll();

    PageFlight findAll(Pageable pageable);

}
