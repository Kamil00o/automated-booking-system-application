package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.reservation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.FlightAlreadyExistsException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.ReservationNotFoundException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.reservation.CreateReservationMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.reservation.ReservationDtoMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.BookingEnterDataCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.reservation.CreateReservationCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.reservation.UpdateReservationCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Reservation;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.ReservationDto;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.ReservationRepository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.ReservationService;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.passenger.PassengerServiceImpl;

import java.util.List;

import static pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.reservation.ReservationConstsImpl.*;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

    private final ReservationRepository reservationRepository;
    private final PassengerServiceImpl passengerService;
    private final CreateReservationMapper createReservationMapper;
    private final ReservationDtoMapper reservationDtoMapper;
    private final ReservationBusinessLogic reservationBL;

    @Transactional
    public ReservationDto bookSeatsInThePlane(BookingEnterDataCommand bookingEnterDataCommand) {
        //CreateReservationCommand createReservationCommand = new CreateReservationCommand()
        return null;
    }

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
                    reservationBL.retrieveReservationEntityFromDb(id), updateReservationCommand.passengerEmail()));
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
        reservationRepository.delete(reservationBL.retrieveReservationEntityFromDb(id));
    }
}
