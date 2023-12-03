package pl.flywithbookedseats.passengeraccountservice.domain.passenger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

import static pl.flywithbookedseats.passengeraccountservice.domain.passenger.PassengerConsts.PASSENGER_ACCOUNT_NOT_UPDATED;
import static pl.flywithbookedseats.passengeraccountservice.domain.passenger.PassengerConsts.PASSENGER_ACCOUNT_WITH_SPECIFIED_EMAIL_EXISTS;

@RequiredArgsConstructor
@Slf4j
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final BookingService bookingService;

    public PagePassenger retrieveAllPassengerAccountsFromDb(Pageable pageable) {
        return passengerRepository.findAll(pageable);
    }

    public Passenger retrievePassengerAccountById(Long id) {
        return retrievePassengerAccountFromDb(id);
    }

    public Passenger retrievePassengerAccountByEmail(String email) {
        return retrievePassengerAccountFromDb(email);
    }

    public Passenger createNewPassengerAccount(Passenger passenger) {
        return generateNewPassengerAccount(passenger);
    }

    public Passenger updatePassengerAccountById(Long id, Passenger passenger) {
        return updatePassengerById(id, passenger);
    }

    public Passenger updatePassengerAccountByEmail(String email, Passenger passenger) {
        return updatePassengerByEmail(email, passenger);
    }

    public void deleteAllPassengerAccounts() {
        passengerRepository.deleteAll();
    }

    public void deletePassengerAccountById(Long id) {
        passengerRepository.deleteById(id);
    }

    public void deletePassengerAccountByEmail(String email) {
        passengerRepository.deleteByEmail(email);
    }

    public Passenger getPassengerDataFromBookingSystem(String email) {
        return getPassengerDataFromBookingService(email);
    }

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

    public boolean exists(Passenger passenger) {
        return passengerRepository.existsByEmail(passenger.getEmail());
    }

    public Passenger getPassengerDataFromBookingService(String email) {
        return bookingService.getPassenger(email);
    }

    public Passenger updatePassengerById(Long id, Passenger passengerUpdateData) {
        Passenger savedPassenger = retrievePassengerAccountFromDb(id);
        return updateSpecifiedPassengerAccount(savedPassenger, passengerUpdateData);
    }

    public Passenger updatePassengerByEmail(String email, Passenger passengerUpdateData) {
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
            log.warn(PASSENGER_ACCOUNT_NOT_UPDATED.formatted(passengerToUpdate.getId(),
                    passengerToUpdate.getEmail()));
            throw new PassengerAlreadyExistsException(PASSENGER_ACCOUNT_WITH_SPECIFIED_EMAIL_EXISTS
                    .formatted(passengerUpdateData.getEmail()));
        }
    }

    public Passenger retrievePassengerAccountFromDb(Long id) {
        return passengerRepository.findById(id);
    }

    public Passenger retrievePassengerAccountFromDb(String email) {
        return passengerRepository.findByEmail(email);
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
