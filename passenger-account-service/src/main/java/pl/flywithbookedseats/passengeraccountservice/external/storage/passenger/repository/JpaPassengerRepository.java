package pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.entity.PassengerEntity;

import java.util.Optional;

@Repository
public interface JpaPassengerRepository extends JpaRepository<PassengerEntity, Long> {

    Optional<PassengerEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
