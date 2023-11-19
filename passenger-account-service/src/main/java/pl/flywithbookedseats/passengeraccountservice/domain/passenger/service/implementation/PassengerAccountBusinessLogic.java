package pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.exceptions.PassengerAccountAlreadyExistsException;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.exceptions.PassengerAccountNotFoundException;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.CreatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.UpdatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.external.passenger.entity.PassengerAccountEntity;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper.CreatePassengerAccountMapper;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper.DtoPassengerAccountMapper;
import pl.flywithbookedseats.passengeraccountservice.external.passenger.repository.PassengerAccountRepository;

import java.util.List;
import java.util.Objects;

import static pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.PassengerAccountConsts.*;

@AllArgsConstructor
@EnableFeignClients
@Component
public class PassengerAccountBusinessLogic {

    private static final Logger logger = LoggerFactory.getLogger(PassengerAccountBusinessLogic.class);

    private final PassengerAccountRepository passengerAccountRepository;
    private final CreatePassengerAccountMapper createPassengerAccountMapper;
    private final DtoPassengerAccountMapper dtoPassengerAccountMapper;
    public final BookingPassengerDtoProxy bookingPassengerDtoProxy;

    public PassengerAccountEntity generateNewPassengerAccount(CreatePassengerAccountCommand createPassengerAccountCommand) {
        if (!exists(createPassengerAccountCommand)) {
            PassengerAccountEntity savedPassengerAccountEntity = createPassengerAccountMapper.apply(createPassengerAccountCommand);
            try {
                savedPassengerAccountEntity
                        .setReservationIdList(retrieveReservationIdListFromPassengerDto(savedPassengerAccountEntity
                                .getEmail()));

            } catch (Exception exception) {
                logger.info("ReservationIdList has not been retrieved from booking service.");
            }
            passengerAccountRepository.save(savedPassengerAccountEntity);
            return savedPassengerAccountEntity;
        } else {
            throw new PassengerAccountAlreadyExistsException(PASSENGER_ACCOUNT_WITH_SPECIFIED_EMAIL_EXISTS
                    .formatted(createPassengerAccountCommand.email()));
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
            passengerAccountRepository.save(savedPassengerAccountEntity);
            return savedPassengerAccountEntity;
        } else {
            logger.warn(PASSENGER_ACCOUNT_NOT_UPDATED.formatted(savedPassengerAccountEntity.getId(),
                    savedPassengerAccountEntity.getEmail()));
            throw new PassengerAccountAlreadyExistsException(PASSENGER_ACCOUNT_WITH_SPECIFIED_EMAIL_EXISTS
                    .formatted(updatePassengerAccountCommand.email()));
        }
    }

    public void deletePassengerAccountById(Long id) {
        passengerAccountRepository.deleteById(id);
    }

    public void deletePassengerAccountByEmail(String email) {
        passengerAccountRepository.deleteByEmail(email);
    }

    public List<Long> retrieveReservationIdListFromPassengerDto(String email) {
        return getPassengerDataFromBookingService(email).reservationsIdList();
    }
    public PassengerAccountDto getPassengerDataFromBookingService(String email) {
        return bookingPassengerDtoProxy.getPassengerDtoData(email);
    }

    public PassengerAccountEntity retrievePassengerAccountFromDb(Long id) {
        return passengerAccountRepository.findById(id)
                .orElseThrow(() -> new PassengerAccountNotFoundException(PASSENGER_ACCOUNT_NOT_FOUND_ID
                        .formatted(id)));
    }

    public PassengerAccountEntity retrievePassengerAccountFromDb(String email) {
        return passengerAccountRepository.findByEmail(email)
                .orElseThrow(() -> new PassengerAccountNotFoundException(PASSENGER_ACCOUNT_NOT_FOUND_EMAIL
                        .formatted(email)));
    }

    public boolean exists(CreatePassengerAccountCommand createPassengerAccountCommand) {
        return passengerAccountRepository.existsByEmail(createPassengerAccountCommand.email());
    }

    public boolean exists(UpdatePassengerAccountCommand updatePassengerAccountCommand,
                          PassengerAccountEntity existingPassengerAccountEntity) {
        String email = updatePassengerAccountCommand.email();
        if (passengerAccountRepository.existsByEmail(email)) {
            return !Objects.equals(retrievePassengerAccountFromDb(email).getId(), existingPassengerAccountEntity.getId());
        }
        return false;
    }
}
