package pl.flywithbookedseats.domain.passenger;

import org.springframework.data.domain.Pageable;

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
