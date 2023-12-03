package pl.flywithbookedseats.passengeraccountservice.external.storage.passenger;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.PassengerNotFoundException;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.PagePassenger;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.Passenger;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.PassengerRepository;

import java.util.List;
import java.util.stream.Collectors;

import static pl.flywithbookedseats.passengeraccountservice.domain.passenger.PassengerConsts.PASSENGER_ACCOUNT_NOT_FOUND_EMAIL;
import static pl.flywithbookedseats.passengeraccountservice.domain.passenger.PassengerConsts.PASSENGER_ACCOUNT_NOT_FOUND_ID;

@RequiredArgsConstructor
public class PassengerStorageAdapter implements PassengerRepository {

    private final PassengerEntityMapper mapper;
    private final JpaPassengerRepository passengerAccountRepository;

    @Override
    public Passenger save(Passenger passenger) {
        PassengerEntity savedPassengerAccount = passengerAccountRepository
                .save(mapper.toEntity(passenger));
        return mapper.toDomain(savedPassengerAccount);
    }

    @Override
    public Passenger findById(Long id) {
        PassengerEntity passengerEntityToFind = passengerAccountRepository
                .findById(id)
                .orElseThrow(() -> new PassengerNotFoundException(PASSENGER_ACCOUNT_NOT_FOUND_ID
                        .formatted(id)));
        return mapper.toDomain(passengerEntityToFind);
    }

    @Override
    public Passenger findByEmail(String email) {
        PassengerEntity passengerEntityToFind = passengerAccountRepository
                .findByEmail(email)
                .orElseThrow(() -> new PassengerNotFoundException(PASSENGER_ACCOUNT_NOT_FOUND_EMAIL
                        .formatted(email)));
        return mapper.toDomain(passengerEntityToFind);
    }

    @Override
    public boolean existsByEmail(String email) {
        return passengerAccountRepository.existsByEmail(email);
    }

    @Override
    public boolean existsById(Long id) {
        return passengerAccountRepository.existsById(id);
    }

    @Override
    public void deleteAll() {
        passengerAccountRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        passengerAccountRepository.deleteById(id);
    }

    @Override
    public void deleteByEmail(String email) {
        passengerAccountRepository.deleteByEmail(email);
    }

    @Override
    public PagePassenger findAll(final Pageable pageable) {
        Page<PassengerEntity> pageOfPassengerAccountsEntity = passengerAccountRepository.findAll(pageable);
        List<Passenger> passengerAccountsOnCurrentPage = pageOfPassengerAccountsEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PagePassenger(passengerAccountsOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfPassengerAccountsEntity.getTotalPages(),
                pageOfPassengerAccountsEntity.getTotalElements());
    }
}
