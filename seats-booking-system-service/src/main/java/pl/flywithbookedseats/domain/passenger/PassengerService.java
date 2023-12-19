package pl.flywithbookedseats.domain.passenger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.flywithbookedseats.api.passenger.PassengerDto;

import java.util.List;

import static pl.flywithbookedseats.domain.passenger.PassengerConstsImpl.PASSENGER_ALREADY_EXISTS_EMAIL;
import static pl.flywithbookedseats.domain.passenger.PassengerConstsImpl.PASSENGER_NOT_CREATED;

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
        return null;
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

    public boolean exists(String email) {
        return repository.existsByEmail(email);
    }

    public boolean exists(Passenger passenger) {
        return repository.existsByEmail(passenger.getEmail());
    }

    /*public boolean exists(UpdatePassengerCommand passengerUpdateData, PassengerEntity existingPassengerEntity) {
        String email = passengerUpdateData.email();
        if (jpaPassengerRepository.existsByEmail(email)) {
            if (Objects.equals(retrievePassengerEntityFromDb(email).getId(), existingPassengerEntity.getId())) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }*/
}
