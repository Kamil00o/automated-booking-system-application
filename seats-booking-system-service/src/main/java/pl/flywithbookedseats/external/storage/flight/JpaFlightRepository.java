package pl.flywithbookedseats.external.storage.flight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaFlightRepository extends JpaRepository<FlightEntity, Long> {

    boolean existsByFlightName(String flightName);

    boolean existsByFlightServiceId(Long id);

    Optional<FlightEntity> findByFlightName(String flightName);

    Optional<FlightEntity> findByFlightServiceId(Long flightServiceId);
}
