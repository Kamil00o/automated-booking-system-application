package pl.flywithbookedseats.domain.reservation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.domain.flight.FlightAlreadyExistsException;
import pl.flywithbookedseats.api.reservation.ReservationDtoMapper1;
import pl.flywithbookedseats.api.reservation.CreateReservationCommand;
import pl.flywithbookedseats.api.reservation.UpdateReservationCommand;
import pl.flywithbookedseats.api.reservation.ReservationDto;
import pl.flywithbookedseats.external.storage.reservation.ReservationEntity;
import pl.flywithbookedseats.external.storage.reservation.JpaReservationRepository;

import java.util.List;

import static pl.flywithbookedseats.domain.reservation.ReservationConstsImpl.*;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

    private final JpaReservationRepository jpaReservationRepository;
    private final ReservationDtoMapper1 reservationDtoMapper1;
    private final ReservationBusinessLogic reservationBL;

    @Transactional
    @Override
    public ReservationDto addNewReservationToDb(CreateReservationCommand createReservationCommand) {
        if (!reservationBL.exists(createReservationCommand)) {
            return reservationDtoMapper1.apply(reservationBL.generateNewReservation(createReservationCommand));
        } else {
            logger.warn(RESERVATION_NOT_CREATED);
            throw new FlightAlreadyExistsException(RESERVATION_ALREADY_EXISTS_SEAT_NUMBER
                    .formatted(createReservationCommand.seatNumber()));
        }
    }

    @Transactional
    @Override
    public ReservationDto updateReservationById(UpdateReservationCommand updateReservationCommand, Long id) {
        if (!reservationBL.exists(updateReservationCommand)) {
            return reservationDtoMapper1.apply(reservationBL.updateSpecifiedReservation(updateReservationCommand,
                    reservationBL.retrieveReservationEntityFromDb(id)));
        } else {
            logger.warn(RESERVATION_NOT_UPDATED.formatted(id));
            throw new FlightAlreadyExistsException(RESERVATION_ALREADY_EXISTS_SEAT_NUMBER
                    .formatted(updateReservationCommand.seatNumber()));
        }
    }

    @Override
    public List<ReservationDto> retrieveAllReservations() {
        return reservationBL.convertIntoListReservationDto(jpaReservationRepository.findAll());
    }

    @Override
    public ReservationDto retrieveReservationById(Long id) {
        return reservationDtoMapper1.apply(reservationBL.retrieveReservationEntityFromDb(id));
    }

    @Override
    public List<ReservationDto> retrieveReservationByEmail(String email) {
        List<ReservationEntity> savedReservationListEntity = jpaReservationRepository.findAllByPassengerEmail(email);
        if (!savedReservationListEntity.isEmpty()) {
            return reservationBL.convertIntoListReservationDto(savedReservationListEntity);
        } else {
            logger.warn(RESERVATIONS_NOT_RETRIEVED);
            throw new ReservationNotFoundException(RESERVATION_NOT_FOUND_EMAIL.formatted(email));
        }
    }

    @Transactional
    @Override
    public void deleteAllReservations() {
        jpaReservationRepository.deleteAll();
    }

    @Transactional
    @Override
    public void deleteReservationById(Long id) {
        reservationBL.deleteReservationById(id);
    }
}
