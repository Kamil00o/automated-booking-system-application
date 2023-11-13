package pl.flywithbookedseats.passengeraccountservice.service.implementation;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.flywithbookedseats.passengeraccountservice.exceptions.PassengerAccountAlreadyExistsException;
import pl.flywithbookedseats.passengeraccountservice.exceptions.PassengerAccountNotFoundException;
import pl.flywithbookedseats.passengeraccountservice.model.command.CreatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.command.UpdatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.domain.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.dto.PassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.model.mapper.CreatePassengerAccountMapper;
import pl.flywithbookedseats.passengeraccountservice.model.mapper.DtoPassengerAccountMapper;
import pl.flywithbookedseats.passengeraccountservice.repository.PassengerAccountRepository;

import java.net.URI;
import java.util.Objects;

import static pl.flywithbookedseats.passengeraccountservice.service.implementation.PassengerAccountConsts.*;

@AllArgsConstructor
@EnableFeignClients
@Component
public class PassengerAccountBusinessLogic {

    private static final Logger logger = LoggerFactory.getLogger(PassengerAccountBusinessLogic.class);

    private final PassengerAccountRepository passengerAccountRepository;
    private final CreatePassengerAccountMapper createPassengerAccountMapper;
    private final DtoPassengerAccountMapper dtoPassengerAccountMapper;
    public final BookingPassengerDtoProxy bookingPassengerDtoProxy;

    public PassengerAccount generateNewPassengerAccount(CreatePassengerAccount createPassengerAccount) {
        if (!exists(createPassengerAccount)) {
            PassengerAccount savedPassengerAccount = createPassengerAccountMapper.apply(createPassengerAccount);
            passengerAccountRepository.save(savedPassengerAccount);
            return savedPassengerAccount;
        } else {
            throw new PassengerAccountAlreadyExistsException(PASSENGER_ACCOUNT_WITH_SPECIFIED_EMAIL_EXISTS
                    .formatted(createPassengerAccount.email()));
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

    public PassengerAccount updateSpecifiedPassengerAccount(UpdatePassengerAccount updatePassengerAccount,
                                                            PassengerAccount savedPassengerAccount) {
        if (!exists(updatePassengerAccount, savedPassengerAccount)) {
            savedPassengerAccount.setName(updatePassengerAccount.name());
            savedPassengerAccount.setSurname(updatePassengerAccount.surname());
            savedPassengerAccount.setEmail(updatePassengerAccount.email());
            savedPassengerAccount.setBirthDate(updatePassengerAccount.birthDate());
            savedPassengerAccount.setDisability(updatePassengerAccount.disability());
            savedPassengerAccount.setReservationIdList(updatePassengerAccount.reservationIdList());
            savedPassengerAccount.setGender(updatePassengerAccount.gender());
            passengerAccountRepository.save(savedPassengerAccount);
            return savedPassengerAccount;
        } else {
            logger.warn(PASSENGER_ACCOUNT_NOT_UPDATED.formatted(savedPassengerAccount.getId(),
                    savedPassengerAccount.getEmail()));
            throw new PassengerAccountAlreadyExistsException(PASSENGER_ACCOUNT_WITH_SPECIFIED_EMAIL_EXISTS
                    .formatted(updatePassengerAccount.email()));
        }
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

    public boolean exists(CreatePassengerAccount createPassengerAccount) {
        return passengerAccountRepository.existsByEmail(createPassengerAccount.email());
    }

    public boolean exists(UpdatePassengerAccount updatePassengerAccount,
                          PassengerAccount existingPassengerAccount) {
        String email = updatePassengerAccount.email();
        if (passengerAccountRepository.existsByEmail(email)) {
            return !Objects.equals(retrievePassengerAccountFromDb(email).getId(), existingPassengerAccount.getId());
        }
        return true;
    }
}
