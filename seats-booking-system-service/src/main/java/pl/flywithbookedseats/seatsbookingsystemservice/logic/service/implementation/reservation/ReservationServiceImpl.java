package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.reservation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.reservation.CreateReservationMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.reservation.ReservationDtoMapper;
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
    @Override
    public ReservationDto addNewReservationToDb(CreateReservationCommand createReservationCommand) {
            Reservation newReservation = createReservationMapper.apply(createReservationCommand);
            String passengerEmail = newReservation.getPassengerEmail();
            if (passengerServiceImpl.exists(passengerEmail)) {
                Passenger savedPassenger = passengerServiceImpl.getPassengerByEmail(passengerEmail);
                newReservation.setPassenger(savedPassenger);
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
    public ReservationDto updateReservationById(UpdateReservationCommand updateReservationCommand) {
        return null;
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
}
