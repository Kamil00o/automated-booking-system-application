package pl.flywithbookedseats.seatsbookingsystemservice.logic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Reservation;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsBySeatNumber(String seatNumber);

    List<Reservation> findAllByPassengerEmail(String passengerEmail);

    Optional<Reservation> findBySeatNumber(String seatNumber);

}
