package pl.flywithbookedseats.domain.seatsbookingsystem;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.api.seatsbookingsystem.BookingEnterDataCommand;
import pl.flywithbookedseats.logic.model.dto.ReservationDto;

@RequiredArgsConstructor
@Service
public class SeatsBookingServiceImpl {

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
