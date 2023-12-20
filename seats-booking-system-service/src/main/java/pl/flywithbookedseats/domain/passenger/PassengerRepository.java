package pl.flywithbookedseats.domain.passenger;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PassengerRepository {

    Passenger save(Passenger passenger);

    boolean existsByEmail(String email);

    Passenger findByEmail(String email);

    Passenger findById(Long id);

    Passenger findByPassengerServiceId(Long id);

    void deleteAll();

    void deleteById(Long id);

    void deleteByEmail(String email);

    PagePassenger findAll(Pageable pageable);

}
