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
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Passenger;
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

            return reservationDtoMapper.apply(newReservation);
    }

    @Transactional
    @Override
    public ReservationDto updateReservationById(UpdateReservationCommand updateReservationCommand, Long id) {
        String passengerEmail = updateReservationCommand.passengerEmail();
        Reservation savedReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(RESERVATION_NOT_FOUND_ID.formatted(id)));

        if (exists(updateReservationCommand)) {
            savedReservation.setSeatNumber(updateReservationCommand.seatNumber());
            savedReservation.setSeatTypeClass(updateReservationCommand.seatTypeClass());
            savedReservation.setPassengerEmail(passengerEmail);
            if (passengerServiceImpl.exists(passengerEmail)) {
                setPassengerDataToReservation(passengerEmail, savedReservation);
            }
            reservationRepository.saveAndFlush(savedReservation);
            return reservationDtoMapper.apply(savedReservation);
        } else {
            logger.warn(RESERVATION_NOT_UPDATED.formatted(id));
            throw new FlightAlreadyExistsException(RESERVATION_ALREADY_EXISTS_SEAT_NUMBER
                    .formatted(updateReservationCommand.seatNumber()));
        }
    }

    @Override
    public List<ReservationDto> retrieveAllReservations() {
        return null;
    }

    @Override
    public ReservationDto retrieveReservationById(Long id) {
        return null;
    }

    @Override
    public List<ReservationDto> retrieveReservationByEmail(String email) {
        return null;
    }

    @Transactional
    @Override
    public void deleteAllReservations() {

    }

    @Transactional
    @Override
    public void deleteReservationById(Long id) {

    }

    private void setPassengerDataToReservation(String passengerEmail, Reservation reservation) {
        Passenger savedPassenger = passengerServiceImpl.getPassengerByEmail(passengerEmail);
        reservation.setPassenger(savedPassenger);
    }

    private boolean exists(UpdateReservationCommand updateReservationCommand) {
        return reservationRepository.existsBySeatNumber(updateReservationCommand.seatNumber());
    }
}
