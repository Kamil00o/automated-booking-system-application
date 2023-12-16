package pl.flywithbookedseats.logic.service.implementation.reservation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.domain.flight.FlightAlreadyExistsException;
import pl.flywithbookedseats.logic.exceptions.ReservationNotFoundException;
import pl.flywithbookedseats.logic.mapper.reservation.ReservationDtoMapper;
import pl.flywithbookedseats.logic.model.command.reservation.CreateReservationCommand;
import pl.flywithbookedseats.logic.model.command.reservation.UpdateReservationCommand;
import pl.flywithbookedseats.logic.model.domain.Reservation;
import pl.flywithbookedseats.logic.model.dto.ReservationDto;
import pl.flywithbookedseats.logic.repository.ReservationRepository;
import pl.flywithbookedseats.logic.service.ReservationService;

import java.util.List;

import static pl.flywithbookedseats.logic.service.implementation.reservation.ReservationConstsImpl.*;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

    private final ReservationRepository reservationRepository;
    private final ReservationDtoMapper reservationDtoMapper;
    private final ReservationBusinessLogic reservationBL;

    @Transactional
    @Override
    public ReservationDto addNewReservationToDb(CreateReservationCommand createReservationCommand) {
        if (!reservationBL.exists(createReservationCommand)) {
            return reservationDtoMapper.apply(reservationBL.generateNewReservation(createReservationCommand));
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
            return reservationDtoMapper.apply(reservationBL.updateSpecifiedReservation(updateReservationCommand,
                    reservationBL.retrieveReservationEntityFromDb(id)));
        } else {
            logger.warn(RESERVATION_NOT_UPDATED.formatted(id));
            throw new FlightAlreadyExistsException(RESERVATION_ALREADY_EXISTS_SEAT_NUMBER
                    .formatted(updateReservationCommand.seatNumber()));
        }
    }

    @Override
    public List<ReservationDto> retrieveAllReservations() {
        return reservationBL.convertIntoListReservationDto(reservationRepository.findAll());
    }

    @Override
    public ReservationDto retrieveReservationById(Long id) {
        return reservationDtoMapper.apply(reservationBL.retrieveReservationEntityFromDb(id));
    }

    @Override
    public List<ReservationDto> retrieveReservationByEmail(String email) {
        List<Reservation> savedReservationList = reservationRepository.findAllByPassengerEmail(email);
        if (!savedReservationList.isEmpty()) {
            return reservationBL.convertIntoListReservationDto(savedReservationList);
        } else {
            logger.warn(RESERVATIONS_NOT_RETRIEVED);
            throw new ReservationNotFoundException(RESERVATION_NOT_FOUND_EMAIL.formatted(email));
        }
    }

    @Transactional
    @Override
    public void deleteAllReservations() {
        reservationRepository.deleteAll();
    }

    @Transactional
    @Override
    public void deleteReservationById(Long id) {
        reservationBL.deleteReservationById(id);
    }
}
