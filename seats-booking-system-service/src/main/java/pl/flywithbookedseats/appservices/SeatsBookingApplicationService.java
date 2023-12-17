package pl.flywithbookedseats.appservices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.api.seatsbookingsystem.BookingEnterDataCommand;
import pl.flywithbookedseats.domain.seatsbookingsystem.SeatsBookingBusinessLogic;
import pl.flywithbookedseats.domain.seatsbookingsystem.SeatsBookingServiceImpl;
import pl.flywithbookedseats.logic.model.dto.ReservationDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class SeatsBookingApplicationService {

    private final SeatsBookingBusinessLogic seatsBookingBL;

    @Transactional
    public ReservationDto bookSeatsInThePlane(BookingEnterDataCommand bookingEnterDataCommand) {
        return seatsBookingBL.bookSeatsInThePlane(bookingEnterDataCommand);
    }

    @Transactional
    public void deleteBookedReservationAndAssociatedData(Long reservationId) {
        seatsBookingBL.deleteBookedReservationAndAssociatedData(reservationId);
    }
}
