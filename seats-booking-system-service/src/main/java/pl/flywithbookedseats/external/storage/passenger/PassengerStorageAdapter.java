package pl.flywithbookedseats.external.storage.passenger;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.PassengerNotFoundException;
import pl.flywithbookedseats.domain.passenger.PassengerRepository;

import static pl.flywithbookedseats.domain.passenger.PassengerConstsImpl.PASSENGER_NOT_FOUND_EMAIL;
import static pl.flywithbookedseats.domain.passenger.PassengerConstsImpl.PASSENGER_NOT_FOUND_ID;

@Slf4j
@RequiredArgsConstructor
public class PassengerStorageAdapter implements PassengerRepository {

    private final JpaPassengerRepository repository;
    private final JpaPassengerRepositoryMapper mapper;

    @Override
    public Passenger save(Passenger passenger) {
        PassengerEntity savedPassengerEntity = repository.save(mapper.toEntity(passenger));

        return mapper.toDomain(savedPassengerEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Passenger findByEmail(String email) {
        PassengerEntity foundPassengerEntity = repository.findByEmail(email)
                .orElseThrow(() -> new PassengerNotFoundException(PASSENGER_NOT_FOUND_EMAIL.formatted(email)));

        return mapper.toDomain(foundPassengerEntity);
    }

    @Override
    public Passenger findById(Long id) {
        PassengerEntity foundPassengerEntity = repository.findById(id)
                .orElseThrow(() -> new PassengerNotFoundException(PASSENGER_NOT_FOUND_ID.formatted(id)));

        return mapper.toDomain(foundPassengerEntity);
    }

    @Override
    public Passenger findByPassengerServiceId(Long id) {
        PassengerEntity foundPassengerEntity = repository.findByPassengerServiceId(id)
                .orElseThrow(() -> new PassengerNotFoundException(PASSENGER_NOT_FOUND_ID.formatted(id)));

        return mapper.toDomain(foundPassengerEntity);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByEmail(String email) {
        repository.deleteByEmail(email);
    }
}
