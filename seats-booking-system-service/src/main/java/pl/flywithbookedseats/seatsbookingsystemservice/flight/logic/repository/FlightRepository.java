package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.domain.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

}
