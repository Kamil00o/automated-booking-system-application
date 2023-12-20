package pl.flywithbookedseats.domain.passenger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.flywithbookedseats.api.passenger.PassengerDto;
import pl.flywithbookedseats.domain.reservation.Reservation;
import pl.flywithbookedseats.external.storage.passenger.PassengerEntity;
import pl.flywithbookedseats.external.storage.reservation.ReservationEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static pl.flywithbookedseats.domain.passenger.PassengerConstsImpl.*;

@Slf4j
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository repository;

    public Passenger createNewPassenger(Passenger passenger) {
        if (!exists(passenger)) {
            try {
                if (passenger.getPassengerServiceId() == null) {
                    passenger.setPassengerServiceId(getPassengerServiceId(passenger.getEmail()));
                }
            } catch (Exception exception) {
                log.info("passengerServiceId not retrieved from the passengerEntity service");
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

    public List<PassengerDto> retrieveAllPassengers() {
        return null;
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

    public Long getPassengerServiceId(String email) {
        return getPassengerAccountDtoData(email).getPassengerServiceId();
    }

    public Passenger getPassengerAccountDtoData(String email) {
        //return passengerAccountProxy.getPassengerAccountDtoData(email);
        return null;
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
