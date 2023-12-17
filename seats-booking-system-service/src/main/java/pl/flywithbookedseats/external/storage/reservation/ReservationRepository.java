package pl.flywithbookedseats.external.storage.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsBySeatNumber(String seatNumber);

    List<Reservation> findAllByPassengerEmail(String passengerEmail);

    Optional<Reservation> findBySeatNumber(String seatNumber);

}
