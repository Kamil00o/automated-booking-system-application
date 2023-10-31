package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.reservation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.reservation.CreateReservationMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.reservation.CreateReservationCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.reservation.UpdateReservationCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Passenger;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Reservation;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.ReservationDto;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.ReservationRepository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.ReservationService;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.passenger.PassengerServiceImpl;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final CreateReservationMapper createReservationMapper;
    private final PassengerServiceImpl passengerServiceImpl;

    @Transactional
    @Override
    public ReservationDto addNewReservationToDb(CreateReservationCommand createReservationCommand) {
        Reservation newReservation = createReservationMapper.apply(createReservationCommand);
        if (passengerServiceImpl.exists(newReservation.getPassengerEmail())) {
            //TODO: retrievePassengerByEmail method in passengerServiceImpl need to be added first
            passengerServiceImpl.retrievePassengerByEmail(newReservation.getPassengerEmail());
        }
        return null;
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
