package pl.flywithbookedseats.seatsbookingsystemservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.flywithbookedseats.seatsbookingsystemservice.model.domain.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

}
