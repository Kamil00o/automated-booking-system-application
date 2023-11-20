package pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.exceptions.PassengerAccountAlreadyExistsException;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.exceptions.PassengerAccountNotFoundException;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.UpdatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.PassengerAccountRepository;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.entity.PassengerAccountEntity;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper.CreatePassengerAccountEntityMapper;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.mapper.DtoPassengerAccountEntityMapper;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.repository.JpaPassengerAccountRepository;

import java.util.List;
import java.util.Objects;

import static pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.PassengerAccountConsts.*;

@AllArgsConstructor
public class PassengerAccountBusinessLogic {

    private static final Logger logger = LoggerFactory.getLogger(PassengerAccountBusinessLogic.class);

    private final JpaPassengerAccountRepository jpaPassengerAccountRepository;
    private final CreatePassengerAccountEntityMapper createPassengerAccountEntityMapper;
    private final DtoPassengerAccountEntityMapper dtoPassengerAccountEntityMapper;
    private final BookingPassengerDtoProxy bookingPassengerDtoProxy;
    private final PassengerAccountRepository passengerAccountRepository;

    public PassengerAccount generateNewPassengerAccount(PassengerAccount passengerAccount) {
        if (!exists(passengerAccount)) {
            PassengerAccount createdPassengerAccount = passengerAccount;
            try {
                createdPassengerAccount
                        .setReservationIdList(retrieveReservationIdListFromPassengerDto(createdPassengerAccount
                                .getEmail()));

            } catch (Exception exception) {
                logger.info("ReservationIdList has not been retrieved from booking service.");
            }

            passengerAccountRepository.save(createdPassengerAccount);
            return createdPassengerAccount;
        } else {
            throw new PassengerAccountAlreadyExistsException(PASSENGER_ACCOUNT_WITH_SPECIFIED_EMAIL_EXISTS
                    .formatted(passengerAccount.getEmail()));
        }
    }

    public PassengerAccountEntity updateSpecifiedPassengerAccount(UpdatePassengerAccountCommand updatePassengerAccountCommand,
                                                                  PassengerAccountEntity savedPassengerAccountEntity) {
        if (!exists(updatePassengerAccountCommand, savedPassengerAccountEntity)) {
            savedPassengerAccountEntity.setName(updatePassengerAccountCommand.name());
            savedPassengerAccountEntity.setSurname(updatePassengerAccountCommand.surname());
            savedPassengerAccountEntity.setEmail(updatePassengerAccountCommand.email());
            savedPassengerAccountEntity.setBirthDate(updatePassengerAccountCommand.birthDate());
            savedPassengerAccountEntity.setDisability(updatePassengerAccountCommand.disability());
            savedPassengerAccountEntity.setReservationIdList(updatePassengerAccountCommand.reservationIdList());
            savedPassengerAccountEntity.setGender(updatePassengerAccountCommand.gender());
            jpaPassengerAccountRepository.save(savedPassengerAccountEntity);
            return savedPassengerAccountEntity;
        } else {
            logger.warn(PASSENGER_ACCOUNT_NOT_UPDATED.formatted(savedPassengerAccountEntity.getId(),
                    savedPassengerAccountEntity.getEmail()));
            throw new PassengerAccountAlreadyExistsException(PASSENGER_ACCOUNT_WITH_SPECIFIED_EMAIL_EXISTS
                    .formatted(updatePassengerAccountCommand.email()));
        }
    }

    public void deletePassengerAccountById(Long id) {
        jpaPassengerAccountRepository.deleteById(id);
    }

    public void deletePassengerAccountByEmail(String email) {
        jpaPassengerAccountRepository.deleteByEmail(email);
    }

    public List<Long> retrieveReservationIdListFromPassengerDto(String email) {
        return getPassengerDataFromBookingService(email).reservationsIdList();
    }
    public PassengerAccountDto getPassengerDataFromBookingService(String email) {
        return bookingPassengerDtoProxy.getPassengerDtoData(email);
    }

    public PassengerAccountEntity retrievePassengerAccountFromDb(Long id) {
        return jpaPassengerAccountRepository.findById(id)
                .orElseThrow(() -> new PassengerAccountNotFoundException(PASSENGER_ACCOUNT_NOT_FOUND_ID
                        .formatted(id)));
    }

    public PassengerAccountEntity retrievePassengerAccountFromDb(String email) {
        return jpaPassengerAccountRepository.findByEmail(email)
                .orElseThrow(() -> new PassengerAccountNotFoundException(PASSENGER_ACCOUNT_NOT_FOUND_EMAIL
                        .formatted(email)));
    }

    public boolean exists(PassengerAccount passengerAccount) {
        return jpaPassengerAccountRepository.existsByEmail(passengerAccount.getEmail());
    }

    public boolean exists(UpdatePassengerAccountCommand updatePassengerAccountCommand,
                          PassengerAccountEntity existingPassengerAccountEntity) {
        String email = updatePassengerAccountCommand.email();
        if (jpaPassengerAccountRepository.existsByEmail(email)) {
            return !Objects.equals(retrievePassengerAccountFromDb(email).getId(), existingPassengerAccountEntity.getId());
        }
        return false;
    }
}
