package pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.PassengerAlreadyExistsException;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.Passenger;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.BookingService;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.PassengerRepository;

import java.util.List;
import java.util.Objects;

import static pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.PassengerConsts.*;

@AllArgsConstructor
public class PassengerBusinessLogic {

    private static final Logger logger = LoggerFactory.getLogger(PassengerBusinessLogic.class);

    private final PassengerRepository passengerRepository;
    private final BookingService bookingService;

    public Passenger generateNewPassengerAccount(Passenger passenger) {
        if (!exists(passenger)) {
            Passenger obtainedPassenger = getPassengerDataFromBookingService(passenger.getEmail());
            if (obtainedPassenger != null) {
                passenger.setReservationIdList(obtainedPassenger.getReservationIdList());
            }

            passengerRepository.save(passenger);
            return passenger;
        } else {
            throw new PassengerAlreadyExistsException(PASSENGER_ACCOUNT_WITH_SPECIFIED_EMAIL_EXISTS
                    .formatted(passenger.getEmail()));
        }
    }

    public Passenger updatePassengerAccountById(Long id, Passenger passengerUpdateData) {
        Passenger savedPassenger = retrievePassengerAccountFromDb(id);
        return updateSpecifiedPassengerAccount(savedPassenger, passengerUpdateData);
    }

    public Passenger updatePassengerAccountByEmail(String email, Passenger passengerUpdateData) {
        Passenger savedPassenger = retrievePassengerAccountFromDb(email);
        return updateSpecifiedPassengerAccount(savedPassenger, passengerUpdateData);
    }

    private Passenger updateSpecifiedPassengerAccount(Passenger passengerToUpdate,
                                                      Passenger passengerUpdateData) {

        if (!exists(passengerUpdateData, passengerToUpdate)) {
            passengerToUpdate.setName(passengerUpdateData.getName());
            passengerToUpdate.setSurname(passengerUpdateData.getSurname());
            passengerToUpdate.setEmail(passengerUpdateData.getEmail());
            passengerToUpdate.setBirthDate(passengerUpdateData.getBirthDate());
            passengerToUpdate.setDisability(passengerUpdateData.isDisability());
            passengerToUpdate.setReservationIdList(passengerUpdateData.getReservationIdList());
            passengerToUpdate.setGender(passengerUpdateData.getGender());
            passengerToUpdate.setNationality(passengerUpdateData.getNationality());
            passengerRepository.save(passengerToUpdate);
            return passengerToUpdate;
        } else {
            logger.warn(PASSENGER_ACCOUNT_NOT_UPDATED.formatted(passengerToUpdate.getId(),
                    passengerToUpdate.getEmail()));
            throw new PassengerAlreadyExistsException(PASSENGER_ACCOUNT_WITH_SPECIFIED_EMAIL_EXISTS
                    .formatted(passengerUpdateData.getEmail()));
        }
    }

    public void deletePassengerAccountById(Long id) {
        passengerRepository.deleteById(id);
    }

    public void deletePassengerAccountByEmail(String email) {
        passengerRepository.deleteByEmail(email);
    }

    public Passenger getPassengerDataFromBookingService(String email) {
        return bookingService.getPassenger(email);
    }

    public Passenger retrievePassengerAccountFromDb(Long id) {
        return passengerRepository.findById(id);
    }

    public Passenger retrievePassengerAccountFromDb(String email) {
        return passengerRepository.findByEmail(email);
    }

    public boolean exists(Passenger passenger) {
        return passengerRepository.existsByEmail(passenger.getEmail());
    }

    public boolean exists(Passenger passenger,
                          Passenger existingPassenger) {
        String email = passenger.getEmail();
        if (passengerRepository.existsByEmail(email)) {
            return !Objects.equals(retrievePassengerAccountFromDb(email).getId(), existingPassenger.getId());
        }
        return false;
    }
}
