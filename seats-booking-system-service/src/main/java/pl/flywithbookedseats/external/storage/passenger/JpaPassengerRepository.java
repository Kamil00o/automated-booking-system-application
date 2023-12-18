package pl.flywithbookedseats.external.storage.passenger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaPassengerRepository extends JpaRepository<PassengerEntity, Long> {

    boolean existsByEmail(String email);

    Optional<PassengerEntity> findByEmail(String email);

    void deleteByEmail(String email);

}
