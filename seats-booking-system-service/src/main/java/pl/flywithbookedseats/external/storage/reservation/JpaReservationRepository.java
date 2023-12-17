package pl.flywithbookedseats.external.storage.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaReservationRepository extends JpaRepository<ReservationEntity, Long> {

    boolean existsBySeatNumber(String seatNumber);

    List<ReservationEntity> findAllByPassengerEmail(String passengerEmail);

    Optional<ReservationEntity> findBySeatNumber(String seatNumber);

    void deleteById(Long id);

}
