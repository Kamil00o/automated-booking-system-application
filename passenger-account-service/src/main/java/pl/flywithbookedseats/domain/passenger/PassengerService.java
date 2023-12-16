package pl.flywithbookedseats.domain.passenger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

import static pl.flywithbookedseats.domain.passenger.PassengerConsts.PASSENGER_ACCOUNT_NOT_UPDATED;
import static pl.flywithbookedseats.domain.passenger.PassengerConsts.PASSENGER_ACCOUNT_WITH_SPECIFIED_EMAIL_EXISTS;

@RequiredArgsConstructor
@Slf4j
public class PassengerService {

    private final PassengerRepository repository;
    private final BookingService bookingService;
    private final ProducerService producerService;

    public PagePassenger retrieveAllPassengersFromDb(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Passenger retrievePassengerById(Long id) {
        return repository.findById(id);
    }

    public Passenger retrievePassengerByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Passenger createNewPassenger(Passenger passenger) {
        return generateNewPassengerAccount(passenger);
    }

    public Passenger updatePassengerById(Long id, Passenger passenger) {
        Passenger savedPassenger = retrievePassengerById(id);
        return updateSpecifiedPassengerAccount(savedPassenger, passenger);
    }

    public Passenger updatePassengerAccountByEmail(String email, Passenger passenger) {
        Passenger savedPassenger = retrievePassengerByEmail(email);
        return updateSpecifiedPassengerAccount(savedPassenger, passenger);
    }

    public void deleteAllPassengerAccounts() {
        repository.deleteAll();
    }

    public void deletePassengerAccountById(Long id) {
        repository.deleteById(id);
    }

    public void deletePassengerAccountByEmail(String email) {
        repository.deleteByEmail(email);
    }

    public Passenger getPassengerDataFromBookingSystem(String email) {
        return getPassengerDataFromBookingService(email);
    }

    public Passenger handlePassengerDataRequest(Passenger passenger) {
        Passenger retrievedPassenger = retrievePassengerByEmail(passenger.getEmail());
        sendRequestedPassengerEvent(retrievedPassenger);
        return retrievedPassenger;
    }

    public void sendRequestedPassengerEvent(Passenger passenger) {
        producerService.sendRequestedPassengerEvent(passenger);
    }

    public void sendUpdatedPassengerEvent(Passenger passenger) {
        producerService.sendUpdatedPassengerEvent(passenger);
    }

    public Passenger generateNewPassengerAccount(Passenger passenger) {
        if (!exists(passenger)) {
            Passenger obtainedPassenger = getPassengerDataFromBookingService(passenger.getEmail());
            if (obtainedPassenger != null) {
                passenger.setReservationsIdList(obtainedPassenger.getReservationsIdList());
            }

            repository.save(passenger);
            return passenger;
        } else {
            throw new PassengerAlreadyExistsException(PASSENGER_ACCOUNT_WITH_SPECIFIED_EMAIL_EXISTS
                    .formatted(passenger.getEmail()));
        }
    }

    public Passenger getPassengerDataFromBookingService(String email) {
        return bookingService.getPassenger(email);
    }

    private Passenger updateSpecifiedPassengerAccount(Passenger passengerToUpdate,
                                                      Passenger passengerUpdateData) {

        if (!exists(passengerUpdateData, passengerToUpdate)) {
            if (passengerUpdateData.getNationality() != null && passengerUpdateData.getGender() != null) {
                passengerToUpdate.setName(passengerUpdateData.getName());
                passengerToUpdate.setSurname(passengerUpdateData.getSurname());
                passengerToUpdate.setEmail(passengerUpdateData.getEmail());
                passengerToUpdate.setBirthDate(passengerUpdateData.getBirthDate());
                passengerToUpdate.setDisability(passengerUpdateData.isDisability());
                passengerToUpdate.setReservationsIdList(passengerUpdateData.getReservationsIdList());
                passengerToUpdate.setGender(passengerUpdateData.getGender());
                passengerToUpdate.setNationality(passengerUpdateData.getNationality());
                repository.save(passengerToUpdate);
            } else {
                passengerToUpdate.setReservationsIdList(passengerUpdateData.getReservationsIdList());
                repository.save(passengerToUpdate);
            }
            return passengerToUpdate;
        } else {
            log.warn(PASSENGER_ACCOUNT_NOT_UPDATED.formatted(passengerToUpdate.getId(),
                    passengerToUpdate.getEmail()));
            throw new PassengerAlreadyExistsException(PASSENGER_ACCOUNT_WITH_SPECIFIED_EMAIL_EXISTS
                    .formatted(passengerUpdateData.getEmail()));
        }
    }

    private boolean exists(Passenger passenger) {
        return repository.existsByEmail(passenger.getEmail());
    }

    private boolean exists(Passenger existingPassenger,
                          Passenger passengerUpdateData) {
        String email = existingPassenger.getEmail();
        if (repository.existsByEmail(email)) {
            return !Objects.equals(retrievePassengerByEmail(email).getId(), passengerUpdateData.getId());
        }

        return false;
    }
}
