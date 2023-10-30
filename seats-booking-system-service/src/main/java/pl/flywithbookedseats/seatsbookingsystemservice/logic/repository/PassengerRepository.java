package pl.flywithbookedseats.seatsbookingsystemservice.logic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    boolean existsByEmail(String email);

}
