package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.domain.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}
