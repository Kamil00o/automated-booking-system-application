package pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.exceptions.PassengerAccountAlreadyExistsException;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.exceptions.PassengerAccountNotFoundException;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.CreatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.UpdatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper.CreatePassengerAccountMapper;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper.DtoPassengerAccountMapper;
import pl.flywithbookedseats.passengeraccountservice.external.passenger.repository.PassengerAccountRepository;

import java.net.URI;
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

    public PassengerAccount generateNewPassengerAccount(CreatePassengerAccountCommand createPassengerAccountCommand) {
        if (!exists(createPassengerAccountCommand)) {
            PassengerAccount savedPassengerAccount = createPassengerAccountMapper.apply(createPassengerAccountCommand);
            try {
                savedPassengerAccount
                        .setReservationIdList(retrieveReservationIdListFromPassengerDto(savedPassengerAccount
                                .getEmail()));

            } catch (Exception exception) {
                logger.info("ReservationIdList has not been retrieved from booking service.");
            }
            passengerAccountRepository.save(savedPassengerAccount);
            return savedPassengerAccount;
        } else {
            throw new PassengerAccountAlreadyExistsException(PASSENGER_ACCOUNT_WITH_SPECIFIED_EMAIL_EXISTS
                    .formatted(createPassengerAccountCommand.email()));
        }
    }

    public ResponseEntity<Object> generateResponseEntity(PassengerAccount passengerAccount) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(passengerAccount.getId())
                .toUri()
                .normalize();

        logger.info("New passenger account for {} {} is being created!", passengerAccount.getName(),
                passengerAccount.getSurname());

        return ResponseEntity.created(location).build();
    }

    public PassengerAccount updateSpecifiedPassengerAccount(UpdatePassengerAccountCommand updatePassengerAccountCommand,
                                                            PassengerAccount savedPassengerAccount) {
        if (!exists(updatePassengerAccountCommand, savedPassengerAccount)) {
            savedPassengerAccount.setName(updatePassengerAccountCommand.name());
            savedPassengerAccount.setSurname(updatePassengerAccountCommand.surname());
            savedPassengerAccount.setEmail(updatePassengerAccountCommand.email());
            savedPassengerAccount.setBirthDate(updatePassengerAccountCommand.birthDate());
            savedPassengerAccount.setDisability(updatePassengerAccountCommand.disability());
            savedPassengerAccount.setReservationIdList(updatePassengerAccountCommand.reservationIdList());
            savedPassengerAccount.setGender(updatePassengerAccountCommand.gender());
            passengerAccountRepository.save(savedPassengerAccount);
            return savedPassengerAccount;
        } else {
            logger.warn(PASSENGER_ACCOUNT_NOT_UPDATED.formatted(savedPassengerAccount.getId(),
                    savedPassengerAccount.getEmail()));
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

    public PassengerAccount retrievePassengerAccountFromDb(Long id) {
        return passengerAccountRepository.findById(id)
                .orElseThrow(() -> new PassengerAccountNotFoundException(PASSENGER_ACCOUNT_NOT_FOUND_ID
                        .formatted(id)));
    }

    public PassengerAccount retrievePassengerAccountFromDb(String email) {
        return passengerAccountRepository.findByEmail(email)
                .orElseThrow(() -> new PassengerAccountNotFoundException(PASSENGER_ACCOUNT_NOT_FOUND_EMAIL
                        .formatted(email)));
    }

    public boolean exists(CreatePassengerAccountCommand createPassengerAccountCommand) {
        return passengerAccountRepository.existsByEmail(createPassengerAccountCommand.email());
    }

    public boolean exists(UpdatePassengerAccountCommand updatePassengerAccountCommand,
                          PassengerAccount existingPassengerAccount) {
        String email = updatePassengerAccountCommand.email();
        if (passengerAccountRepository.existsByEmail(email)) {
            return !Objects.equals(retrievePassengerAccountFromDb(email).getId(), existingPassengerAccount.getId());
        }
        return false;
    }
}
