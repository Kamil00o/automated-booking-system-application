package pl.flywithbookedseats.logic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.flywithbookedseats.logic.model.domain.Flight;

import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    boolean existsByFlightName(String flightName);

    boolean existsByFlightServiceId(Long id);

    Optional<Flight> findByFlightName(String flightName);

    Optional<Flight> findByFlightServiceId(Long flightServiceId);
}