package pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.adapter;

import lombok.RequiredArgsConstructor;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.PassengerAccountRepository;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.entity.PassengerAccountEntity;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.mapper.PassengerAccountEntityMapper1;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.repository.JpaPassengerAccountRepository;

@RequiredArgsConstructor
public class PassengerAccountStorageAdapter implements PassengerAccountRepository {

    private final PassengerAccountEntityMapper1 mapper;
    private final JpaPassengerAccountRepository passengerAccountRepository;

    @Override
    public PassengerAccount save(PassengerAccount passengerAccount) {
        PassengerAccountEntity savedPassengerAccount = passengerAccountRepository.save(mapper.toEntity(passengerAccount));
        return mapper.toDomain(savedPassengerAccount);
    }
}
