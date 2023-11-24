package pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.exceptions.PassengerAccountNotFoundException;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PagePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.PassengerAccountRepository;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.entity.PassengerAccountEntity;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.mapper.PassengerAccountEntityMapper;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.repository.JpaPassengerAccountRepository;

import java.util.List;
import java.util.stream.Collectors;

import static pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.PassengerAccountConsts.PASSENGER_ACCOUNT_NOT_FOUND_EMAIL;
import static pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.PassengerAccountConsts.PASSENGER_ACCOUNT_NOT_FOUND_ID;

@RequiredArgsConstructor
public class PassengerAccountStorageAdapter implements PassengerAccountRepository {

    private final PassengerAccountEntityMapper mapper;
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
    public PassengerAccount findByEmail(String email) {
        PassengerAccountEntity passengerAccountEntityToFind = passengerAccountRepository
                .findByEmail(email)
                .orElseThrow(() -> new PassengerAccountNotFoundException(PASSENGER_ACCOUNT_NOT_FOUND_EMAIL
                        .formatted(email)));
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

    @Override
    public PagePassengerAccount findAll(final Pageable pageable) {
        Page<PassengerAccountEntity> pageOfPassengerAccountsEntity = passengerAccountRepository.findAll(pageable);
        List<PassengerAccount> passengerAccountsOnCurrentPage = pageOfPassengerAccountsEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PagePassengerAccount(passengerAccountsOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfPassengerAccountsEntity.getTotalPages(),
                pageOfPassengerAccountsEntity.getTotalElements());
    }
}
