package pl.flywithbookedseats.external.storage.passenger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaPassengerRepository extends JpaRepository<PassengerEntity, Long> {

    Optional<PassengerEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
