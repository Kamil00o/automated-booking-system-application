package pl.flywithbookedseats.passengeraccountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.flywithbookedseats.passengeraccountservice.model.domain.PassengerAccount;

@Repository
public interface PassengerAccountRepository extends JpaRepository<PassengerAccount, Long> {

}
