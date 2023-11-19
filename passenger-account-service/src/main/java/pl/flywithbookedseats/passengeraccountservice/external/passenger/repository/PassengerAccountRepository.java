package pl.flywithbookedseats.passengeraccountservice.external.passenger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.flywithbookedseats.passengeraccountservice.external.passenger.entity.PassengerAccountEntity;

import java.util.Optional;

@Repository
public interface PassengerAccountRepository extends JpaRepository<PassengerAccountEntity, Long> {

    Optional<PassengerAccountEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
