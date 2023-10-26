package pl.flywithbookedseats.seatsbookingsystemservice.logic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    boolean existsByFlightName(String flightName);

}
