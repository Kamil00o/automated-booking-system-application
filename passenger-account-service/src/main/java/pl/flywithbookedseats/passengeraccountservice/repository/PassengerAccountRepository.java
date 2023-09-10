package pl.flywithbookedseats.passengeraccountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.flywithbookedseats.passengeraccountservice.model.domain.PassengerAccount;

import java.util.List;

@Repository
public interface PassengerAccountRepository extends JpaRepository<PassengerAccount, Long> {

    /*@Query("SELECT u FROM passenger_account_TABLE WHERE u.id = :id")
    List<PassengerAccount> getPassengerAccountById(long id);*/
}
