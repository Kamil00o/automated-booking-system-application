package pl.flywithbookedseats.external.storage.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.flywithbookedseats.api.reservation.ReservationDto;
import pl.flywithbookedseats.domain.reservation.Reservation;
import pl.flywithbookedseats.domain.reservation.ReservationDatabaseIsEmptyException;
import pl.flywithbookedseats.domain.reservation.ReservationNotFoundException;
import pl.flywithbookedseats.domain.reservation.ReservationRepository;

import java.util.ArrayList;
import java.util.List;

import static pl.flywithbookedseats.domain.reservation.ReservationConstsImpl.*;

@Slf4j
@RequiredArgsConstructor
public class ReservationAdapterRepository implements ReservationRepository {

    private final JpaReservationRepositoryMapper mapper;
    private final JpaReservationRepository repository;

    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity savedReservationEntity = repository.save(mapper.toEntityy(reservation));
        return mapper.toDomain(savedReservationEntity);
    }

    @Override
    public boolean existsBySeatNumber(String seatNumber) {
        return repository.existsBySeatNumber(seatNumber);
    }

    @Override
    public Reservation findById(Long id) {
        ReservationEntity foundReservationEntity = repository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(RESERVATION_NOT_FOUND_ID.formatted(id)));
        return mapper.toDomain(foundReservationEntity);
    }

    @Override
    public List<Reservation> findAllByPassengerEmail(String passengerEmail) {
        List<ReservationEntity> savedReservationEntityList = repository.findAllByPassengerEmail(passengerEmail);
        if (!savedReservationEntityList.isEmpty()) {
            return convertIntoListReservationDto(savedReservationEntityList);
        } else {
            log.warn(RESERVATIONS_NOT_RETRIEVED);
            throw new ReservationNotFoundException(RESERVATION_NOT_FOUND_EMAIL.formatted(passengerEmail));
        }
    }

    private List<Reservation> convertIntoListReservationDto(List<ReservationEntity> localSavedReservationListEntity) {
        if (!localSavedReservationListEntity.isEmpty()) {
            List<Reservation> savedReservationDtoList = new ArrayList<>();
            localSavedReservationListEntity.forEach(reservation -> savedReservationDtoList
                    .add(mapper.toDomain(reservation)));
            return savedReservationDtoList;
        } else {
            log.warn(RESERVATIONS_NOT_RETRIEVED);
            throw new ReservationDatabaseIsEmptyException(RESERVATION_DATABASE_IS_EMPTY_EXCEPTION);
        }
    }
}
