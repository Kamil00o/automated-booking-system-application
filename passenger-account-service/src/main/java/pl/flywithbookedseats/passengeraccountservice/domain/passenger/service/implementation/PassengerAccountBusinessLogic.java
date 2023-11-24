package pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.exceptions.PassengerAccountAlreadyExistsException;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.BookingPassengerDtoProxyService;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.PassengerAccountRepository;

import java.util.List;
import java.util.Objects;

import static pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.PassengerAccountConsts.*;

@AllArgsConstructor
public class PassengerAccountBusinessLogic {

    private static final Logger logger = LoggerFactory.getLogger(PassengerAccountBusinessLogic.class);

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

    public PassengerAccount updatePassengerAccountById(Long id, PassengerAccount passengerAccountUpdateData) {
        PassengerAccount savedPassengerAccount = retrievePassengerAccountFromDb(id);
        return updateSpecifiedPassengerAccount(savedPassengerAccount, passengerAccountUpdateData);
    }

    public PassengerAccount updatePassengerAccountByEmail(String email, PassengerAccount passengerAccountUpdateData) {
        PassengerAccount savedPassengerAccount = retrievePassengerAccountFromDb(email);
        return updateSpecifiedPassengerAccount(savedPassengerAccount, passengerAccountUpdateData);
    }

    private PassengerAccount updateSpecifiedPassengerAccount(PassengerAccount passengerAccountToUpdate,
                                                             PassengerAccount passengerAccountUpdateData) {

        if (!exists(passengerAccountUpdateData, passengerAccountToUpdate)) {
            passengerAccountToUpdate.setName(passengerAccountUpdateData.getName());
            passengerAccountToUpdate.setSurname(passengerAccountUpdateData.getSurname());
            passengerAccountToUpdate.setEmail(passengerAccountUpdateData.getEmail());
            passengerAccountToUpdate.setBirthDate(passengerAccountUpdateData.getBirthDate());
            passengerAccountToUpdate.setDisability(passengerAccountUpdateData.isDisability());
            passengerAccountToUpdate.setReservationIdList(passengerAccountUpdateData.getReservationIdList());
            passengerAccountToUpdate.setGender(passengerAccountUpdateData.getGender());
            passengerAccountToUpdate.setNationality(passengerAccountUpdateData.getNationality());
            passengerAccountRepository.save(passengerAccountToUpdate);
            return passengerAccountToUpdate;
        } else {
            logger.warn(PASSENGER_ACCOUNT_NOT_UPDATED.formatted(passengerAccountToUpdate.getId(),
                    passengerAccountToUpdate.getEmail()));
            throw new PassengerAccountAlreadyExistsException(PASSENGER_ACCOUNT_WITH_SPECIFIED_EMAIL_EXISTS
                    .formatted(passengerAccountUpdateData.getEmail()));
        }
    }

    public void deletePassengerAccountById(Long id) {
        passengerAccountRepository.deleteById(id);
    }

    public void deletePassengerAccountByEmail(String email) {
        passengerAccountRepository.deleteByEmail(email);
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
        return passengerAccountRepository.existsByEmail(passengerAccount.getEmail());
    }

    public boolean exists(PassengerAccount passengerAccount,
                          PassengerAccount existingPassengerAccount) {
        String email = passengerAccount.getEmail();
        if (passengerAccountRepository.existsByEmail(email)) {
            return !Objects.equals(retrievePassengerAccountFromDb(email).getId(), existingPassengerAccount.getId());
        }
        return false;
    }
}
