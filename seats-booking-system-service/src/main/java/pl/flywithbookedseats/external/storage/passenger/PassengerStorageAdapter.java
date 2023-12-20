package pl.flywithbookedseats.external.storage.passenger;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.flywithbookedseats.domain.passenger.PagePassenger;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.PassengerNotFoundException;
import pl.flywithbookedseats.domain.passenger.PassengerRepository;
import pl.flywithbookedseats.domain.reservation.PageReservation;
import pl.flywithbookedseats.domain.reservation.Reservation;
import pl.flywithbookedseats.external.storage.reservation.ReservationEntity;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public PagePassenger findAll(Pageable pageable) {
        Page<PassengerEntity> pageOfPassengerEntities = repository.findAll(pageable);
        List<Passenger> passengersOnCurrentPage = pageOfPassengerEntities
                .getContent()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PagePassenger(passengersOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfPassengerEntities.getTotalPages(),
                pageOfPassengerEntities.getTotalElements());
    }
}
