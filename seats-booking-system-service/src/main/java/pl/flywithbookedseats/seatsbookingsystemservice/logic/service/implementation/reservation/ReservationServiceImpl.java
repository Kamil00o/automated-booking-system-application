package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.reservation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.FlightAlreadyExistsException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.ReservationDatabaseIsEmptyException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.ReservationNotFoundException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.reservation.CreateReservationMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.reservation.ReservationDtoMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.BookingEnterDataCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.reservation.CreateReservationCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.reservation.UpdateReservationCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Passenger;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Reservation;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.ReservationDto;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.ReservationRepository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.ReservationService;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.passenger.PassengerServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.reservation.ReservationConstsImpl.*;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

    private final ReservationRepository reservationRepository;
    private final PassengerServiceImpl passengerServiceImpl;
    private final CreateReservationMapper createReservationMapper;
    private final ReservationDtoMapper reservationDtoMapper;

    @Transactional
    public ReservationDto bookSeatsInThePlane(BookingEnterDataCommand bookingEnterDataCommand) {
        //CreateReservationCommand createReservationCommand = new CreateReservationCommand()
        return null;
    }

    @Transactional
    @Override
    public ReservationDto addNewReservationToDb(CreateReservationCommand createReservationCommand) {
        if (!exists(createReservationCommand)) {
            return reservationDtoMapper.apply(generateNewReservation(createReservationCommand));
        }
        logger.warn(RESERVATION_NOT_CREATED);
        throw new FlightAlreadyExistsException(RESERVATION_ALREADY_EXISTS_SEAT_NUMBER
                .formatted(createReservationCommand.seatNumber()));
    }

    @Transactional
    @Override
    public ReservationDto updateReservationById(UpdateReservationCommand updateReservationCommand, Long id) {
        String passengerEmail = updateReservationCommand.passengerEmail();
        Reservation savedReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(RESERVATION_NOT_FOUND_ID.formatted(id)));

        if (!exists(updateReservationCommand)) {
            return reservationDtoMapper.apply(updateSpecifiedReservation(updateReservationCommand,
                    savedReservation, passengerEmail));
        } else {
            logger.warn(RESERVATION_NOT_UPDATED.formatted(id));
            throw new FlightAlreadyExistsException(RESERVATION_ALREADY_EXISTS_SEAT_NUMBER
                    .formatted(updateReservationCommand.seatNumber()));
        }
    }

    @Override
    public List<ReservationDto> retrieveAllReservations() {
        List<Reservation> savedReservationList = reservationRepository.findAll();
        return convertIntoListReservationDto(savedReservationList);
    }

    @Override
    public ReservationDto retrieveReservationById(Long id) {
        Reservation savedReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(RESERVATION_NOT_FOUND_ID.formatted(id)));

        return reservationDtoMapper.apply(savedReservation);
    }

    @Override
    public List<ReservationDto> retrieveReservationByEmail(String email) {
        List<Reservation> savedReservationList = reservationRepository.findAllByPassengerEmail(email);
        if (!savedReservationList.isEmpty()) {
            return convertIntoListReservationDto(savedReservationList);
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
        Reservation savedReservation = reservationRepository
                .findById(id).orElseThrow(() ->
                    new ReservationNotFoundException(RESERVATION_NOT_DELETED_ID.formatted(id)));
        reservationRepository.delete(savedReservation);
    }

    private void setPassengerDataToReservation(String passengerEmail, Reservation reservation) {
        Passenger savedPassenger = passengerServiceImpl.getPassengerByEmail(passengerEmail);
        reservation.setPassenger(savedPassenger);
    }

    private List<ReservationDto> convertIntoListReservationDto(List<Reservation> localSavedReservationList) {
        if (!localSavedReservationList.isEmpty()) {
            List<ReservationDto> savedReservationDtoList = new ArrayList<>();
            localSavedReservationList.forEach(reservation -> savedReservationDtoList
                    .add(reservationDtoMapper.apply(reservation)));
            return savedReservationDtoList;
        } else {
            logger.warn(RESERVATIONS_NOT_RETRIEVED);
            throw new ReservationDatabaseIsEmptyException(RESERVATION_DATABASE_IS_EMPTY_EXCEPTION);
        }
    }

    private Reservation generateNewReservation(CreateReservationCommand createReservationCommand) {
        Reservation newReservation = createReservationMapper.apply(createReservationCommand);
        String passengerEmail = newReservation.getPassengerEmail();
        if (passengerServiceImpl.exists(passengerEmail)) {
            setPassengerDataToReservation(passengerEmail, newReservation);
            reservationRepository.save(newReservation);
            logger.info(RESERVATION_ADDED_PASSENGER);
        } else {
            reservationRepository.save(newReservation);
            logger.info(RESERVATION_ADDED_NO_PASSENGER);
        }

        return newReservation;
    }

    private Reservation updateSpecifiedReservation(UpdateReservationCommand updateReservationCommand,
                                                   Reservation savedReservation, String passengerEmail) {
        savedReservation.setSeatNumber(updateReservationCommand.seatNumber());
        savedReservation.setSeatTypeClass(updateReservationCommand.seatTypeClass());
        savedReservation.setPassengerEmail(passengerEmail);
        if (passengerServiceImpl.exists(passengerEmail)) {
            setPassengerDataToReservation(passengerEmail, savedReservation);
        }
        reservationRepository.saveAndFlush(savedReservation);

        return savedReservation;
    }

    private boolean exists(UpdateReservationCommand updateReservationCommand) {
        return reservationRepository.existsBySeatNumber(updateReservationCommand.seatNumber());
    }

    private boolean exists(CreateReservationCommand createReservationCommand) {
        return reservationRepository.existsBySeatNumber(createReservationCommand.seatNumber());
    }
}
