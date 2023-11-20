package pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.entity.PassengerAccountEntity;

import java.util.Optional;

@Repository
public interface JpaPassengerAccountRepository extends JpaRepository<PassengerAccountEntity, Long> {

    Optional<PassengerAccountEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
