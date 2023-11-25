package pl.flywithbookedseats.passengeraccountservice.domain.passenger.service;

import org.springframework.data.domain.Pageable;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PagePassenger;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.Passenger;

public interface PassengerRepository {

    Passenger save(Passenger passenger);

    Passenger findById(Long id);

    Passenger findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsById(Long id);

    void deleteAll();

    void deleteById(Long id);

    void deleteByEmail(String email);

    PagePassenger findAll(final Pageable pageable);
}
