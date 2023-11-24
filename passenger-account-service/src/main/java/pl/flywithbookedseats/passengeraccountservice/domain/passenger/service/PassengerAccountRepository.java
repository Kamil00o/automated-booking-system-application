package pl.flywithbookedseats.passengeraccountservice.domain.passenger.service;

import org.springframework.data.domain.Pageable;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PagePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;

public interface PassengerAccountRepository {

    PassengerAccount save(PassengerAccount passengerAccount);

    PassengerAccount findById(Long id);

    PassengerAccount findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsById(Long id);

    void deleteAll();

    void deleteById(Long id);

    PagePassengerAccount findAll(final Pageable pageable);
}
