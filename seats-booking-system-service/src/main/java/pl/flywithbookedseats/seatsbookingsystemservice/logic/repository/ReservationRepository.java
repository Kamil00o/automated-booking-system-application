package pl.flywithbookedseats.seatsbookingsystemservice.logic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
