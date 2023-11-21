package pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.adapter;

import lombok.RequiredArgsConstructor;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.exceptions.PassengerAccountNotFoundException;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.PassengerAccountRepository;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.entity.PassengerAccountEntity;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.mapper.PassengerAccountEntityMapper1;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.repository.JpaPassengerAccountRepository;

import static pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.PassengerAccountConsts.PASSENGER_ACCOUNT_NOT_FOUND_ID;

@RequiredArgsConstructor
public class PassengerAccountStorageAdapter implements PassengerAccountRepository {

    private final PassengerAccountEntityMapper1 mapper;
    private final JpaPassengerAccountRepository passengerAccountRepository;

    @Override
    public PassengerAccount save(PassengerAccount passengerAccount) {
        PassengerAccountEntity savedPassengerAccount = passengerAccountRepository
                .save(mapper.toEntity(passengerAccount));
        return mapper.toDomain(savedPassengerAccount);
    }

    @Override
    public PassengerAccount findById(Long id) {
        PassengerAccountEntity passengerAccountEntityToFind = passengerAccountRepository
                .findById(id)
                .orElseThrow(() -> new PassengerAccountNotFoundException(PASSENGER_ACCOUNT_NOT_FOUND_ID
                .formatted(id)));
        return mapper.toDomain(passengerAccountEntityToFind);
    }

    @Override
    public void deleteAll() {
        passengerAccountRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        passengerAccountRepository.deleteById(id);
    }
}
