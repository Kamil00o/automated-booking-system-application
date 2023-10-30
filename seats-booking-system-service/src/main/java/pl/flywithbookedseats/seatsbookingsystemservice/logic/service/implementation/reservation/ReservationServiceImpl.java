package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.reservation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.reservation.CreateReservationCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.reservation.UpdateReservationCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.ReservationDto;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.ReservationRepository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.ReservationService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Transactional
    @Override
    public ReservationDto createNewReservation(CreateReservationCommand createReservationCommand) {

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
