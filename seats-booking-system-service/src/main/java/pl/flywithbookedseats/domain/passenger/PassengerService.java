package pl.flywithbookedseats.domain.passenger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import pl.flywithbookedseats.domain.reservation.Reservation;

import java.util.List;
import java.util.Objects;

import static pl.flywithbookedseats.domain.passenger.PassengerConstsImpl.*;

@Slf4j
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository repository;
    private final PassengerAccountService passengerAccountService;

    public Passenger createNewPassenger(Passenger passenger) {
        if (!exists(passenger)) {
            Passenger passengerAccount = getPassengerAccountDtoData(passenger.getEmail());
            System.out.println(passengerAccount.getPassengerServiceId().toString());
            if (passengerAccount != null) {
                passenger.setPassengerServiceId(passengerAccount.getPassengerServiceId());
            }

            /*List<ReservationEntity> reservationsToAddList = parseReservationIdToReservationEntity(passenger
                    .reservationsIdList());
            if (reservationsToAddList != null) {
                reservationsToAddList.forEach(reservation -> addReservationEntityToPassengerEntity(passenger, reservation));
            }*/

            return repository.save(passenger);
        } else {
            log.warn(PASSENGER_NOT_CREATED);
            throw new PassengerAlreadyExistsException(PASSENGER_ALREADY_EXISTS_EMAIL
                    .formatted(passenger.getEmail()));
        }
    }

    public Passenger updatePassengerByEmail(Passenger passenger, String email) {
        Passenger savedPassenger = retrievePassengerByEmail(email);

        return updateSpecifiedPassenger(passenger, savedPassenger, false);
    }

    public Passenger updatePassengerById(Passenger passenger, Long id) {
        Passenger savedPassenger = retrievePassengerById(id);

        return updateSpecifiedPassenger(passenger, savedPassenger, false);
    }

    public Passenger updateSpecifiedPassenger(Passenger passengerUpdateData, Passenger passengerToUpdate,
                                              boolean doNotSaveInDb) {

        String email = passengerUpdateData.getEmail();
        if (!exists(passengerUpdateData, passengerToUpdate)) {
            addReservationEntityToPassengerEntity(passengerToUpdate, passengerUpdateData);
            passengerToUpdate.setBirthDate(passengerUpdateData.getBirthDate());
            passengerToUpdate.setName(passengerUpdateData.getName());
            passengerToUpdate.setSurname(passengerUpdateData.getSurname());
            passengerToUpdate.setEmail(email);
            passengerToUpdate.setDisability(passengerUpdateData.isDisability());
            savePassengerEntityInDb(doNotSaveInDb, passengerToUpdate);

            //TODO:FeignClient later
            //passengerBL.sendUpdatedPassengerEvent(updatedPassengerDto);
            return passengerToUpdate;
        } else {
            log.warn(PASSENGER_NOT_UPDATED);
            throw new PassengerAlreadyExistsException(PASSENGER_ALREADY_EXISTS_EMAIL.formatted(email));
        }
    }

    public Passenger retrievePassengerByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Passenger retrievePassengerById(Long id) {
        return repository.findById(id);
    }

    public Passenger retrievePassengerByPassengerServiceId(Long id) {
        return repository.findByPassengerServiceId(id);
    }

    public PagePassenger retrieveAllPassengers(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public void deleteAllPassengers() {
        repository.deleteAll();
    }

    public void deletePassengerById(Long id) {
        repository.deleteById(id);
    }

    public void deletePassengerByEmail(String email) {
        repository.deleteByEmail(email);
    }

    public Passenger getPassengerAccountDtoData(String email) {
        return passengerAccountService.getPassengerAccountData(email);
    }

    public void savePassengerEntityInDb(boolean skipSaving, Passenger passenger) {
        if (!skipSaving) {
            repository.save(passenger);
        }
    }

    private void addReservationEntityToPassengerEntity(Passenger passengerToUpdate, Passenger passengerUpdateData) {
        List<Reservation> reservationNewList = passengerUpdateData.getReservationsList();
        if (reservationNewList != null && !reservationNewList.isEmpty()) {
            reservationNewList.forEach(reservation -> passengerToUpdate.getReservationsList().add(reservation));
        }
    }

    public boolean exists(String email) {
        return repository.existsByEmail(email);
    }

    public boolean exists(Passenger passenger) {
        return repository.existsByEmail(passenger.getEmail());
    }

    public boolean exists(Passenger passengerUpdateData, Passenger passengerToUpdate) {
        String email = passengerUpdateData.getEmail();
        if (repository.existsByEmail(email)) {
            return !Objects.equals(passengerToUpdate.getEmail(), passengerUpdateData.getEmail());
        } else {
            return false;
        }
    }
}
