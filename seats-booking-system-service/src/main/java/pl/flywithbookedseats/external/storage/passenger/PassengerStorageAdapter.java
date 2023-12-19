package pl.flywithbookedseats.external.storage.passenger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.PassengerRepository;

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
}
