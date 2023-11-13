package pl.flywithbookedseats.passengeraccountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.flywithbookedseats.passengeraccountservice.model.domain.PassengerAccount;

import java.util.Optional;

@Repository
public interface PassengerAccountRepository extends JpaRepository<PassengerAccount, Long> {

    Optional<PassengerAccount> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
