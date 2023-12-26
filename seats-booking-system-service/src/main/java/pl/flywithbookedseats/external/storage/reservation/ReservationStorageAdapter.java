package pl.flywithbookedseats.external.storage.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.flywithbookedseats.domain.reservation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static pl.flywithbookedseats.domain.reservation.ReservationConstsImpl.*;

@Slf4j
@RequiredArgsConstructor
public class ReservationStorageAdapter implements ReservationRepository {

    private final JpaReservationRepositoryMapper mapper;
    private final JpaReservationRepository repository;

    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity savedReservationEntity = repository.save(mapper.toEntity(reservation));
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

    @Override
    public Reservation findBySeatNumber(String seatNumber) {
        ReservationEntity foundReservationEntity = repository.findBySeatNumber(seatNumber)
                .orElseThrow(() ->
                        new ReservationNotFoundException(RESERVATION_NOT_FOUND_SEAT_NUMBER.formatted(seatNumber)));

        return mapper.toDomain(foundReservationEntity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public PageReservation findAll(Pageable pageable) {
        Page<ReservationEntity> pageOfReservationEntities = repository.findAll(pageable);
        List<Reservation> reservationsOnCurrentPage = pageOfReservationEntities
                .getContent()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PageReservation(reservationsOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfReservationEntities.getTotalPages(),
                pageOfReservationEntities.getTotalElements());
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
