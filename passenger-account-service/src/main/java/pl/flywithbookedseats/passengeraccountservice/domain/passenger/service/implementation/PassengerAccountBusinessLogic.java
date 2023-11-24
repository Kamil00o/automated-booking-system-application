package pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.exceptions.PassengerAccountAlreadyExistsException;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.BookingPassengerDtoProxyService;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.PassengerAccountRepository;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.repository.JpaPassengerAccountRepository;

import java.util.List;
import java.util.Objects;

import static pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.PassengerAccountConsts.*;

@AllArgsConstructor
public class PassengerAccountBusinessLogic {

    private static final Logger logger = LoggerFactory.getLogger(PassengerAccountBusinessLogic.class);

    private final JpaPassengerAccountRepository jpaPassengerAccountRepository;
    private final PassengerAccountRepository passengerAccountRepository;
    private final BookingPassengerDtoProxyService bookingPassengerDtoProxyService;

    public PassengerAccount generateNewPassengerAccount(PassengerAccount passengerAccount) {
        if (!exists(passengerAccount)) {
            PassengerAccount createdPassengerAccount = passengerAccount;
            try {
                createdPassengerAccount
                        .setReservationIdList(retrieveReservationIdListFromPassengerAccount(createdPassengerAccount
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

    protected PassengerAccount updateSpecifiedPassengerAccount(Long id, PassengerAccount passengerAccount) {
        PassengerAccount savedPassengerAccount = retrievePassengerAccountFromDb(id);
        if (!exists(passengerAccount, savedPassengerAccount)) {
            savedPassengerAccount.setName(passengerAccount.getName());
            savedPassengerAccount.setSurname(passengerAccount.getSurname());
            savedPassengerAccount.setEmail(passengerAccount.getEmail());
            savedPassengerAccount.setBirthDate(passengerAccount.getBirthDate());
            savedPassengerAccount.setDisability(passengerAccount.isDisability());
            savedPassengerAccount.setReservationIdList(passengerAccount.getReservationIdList());
            savedPassengerAccount.setGender(passengerAccount.getGender());
            savedPassengerAccount.setNationality(passengerAccount.getNationality());
            passengerAccountRepository.save(savedPassengerAccount);
            return savedPassengerAccount;
        } else {
            logger.warn(PASSENGER_ACCOUNT_NOT_UPDATED.formatted(savedPassengerAccount.getId(),
                    savedPassengerAccount.getEmail()));
            throw new PassengerAccountAlreadyExistsException(PASSENGER_ACCOUNT_WITH_SPECIFIED_EMAIL_EXISTS
                    .formatted(passengerAccount.getEmail()));
        }
    }

    public void deletePassengerAccountById(Long id) {
        passengerAccountRepository.deleteById(id);
    }

    public void deletePassengerAccountByEmail(String email) {
        jpaPassengerAccountRepository.deleteByEmail(email);
    }

    public List<Long> retrieveReservationIdListFromPassengerAccount(String email) {
        return getPassengerDataFromBookingService(email).getReservationIdList();
    }

    public PassengerAccount getPassengerDataFromBookingService(String email) {
        return bookingPassengerDtoProxyService.getPassengerDtoFromBookingService(email);
    }

    public PassengerAccount retrievePassengerAccountFromDb(Long id) {
        return passengerAccountRepository.findById(id);
    }

    public PassengerAccount retrievePassengerAccountFromDb(String email) {
        return passengerAccountRepository.findByEmail(email);
    }

    public boolean exists(PassengerAccount passengerAccount) {
        return jpaPassengerAccountRepository.existsByEmail(passengerAccount.getEmail());
    }

    public boolean exists(PassengerAccount passengerAccount,
                          PassengerAccount existingPassengerAccount) {
        String email = passengerAccount.getEmail();
        if (jpaPassengerAccountRepository.existsByEmail(email)) {
            return !Objects.equals(retrievePassengerAccountFromDb(email).getId(), existingPassengerAccount.getId());
        }
        return false;
    }
}
