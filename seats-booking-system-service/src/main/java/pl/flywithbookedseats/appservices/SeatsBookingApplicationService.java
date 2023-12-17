package pl.flywithbookedseats.appservices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.domain.seatsbookingsystem.BookingEnterData;
import pl.flywithbookedseats.domain.seatsbookingsystem.SeatsBookingService;
import pl.flywithbookedseats.api.reservation.ReservationDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class SeatsBookingApplicationService {

    private final SeatsBookingService service;

    @Transactional
    public ReservationDto bookSeatsInThePlane(BookingEnterData bookingEnterData) {
        return service.bookSeatsInThePlane(bookingEnterData);
    }

    @Transactional
    public void deleteBookedReservationAndAssociatedData(Long reservationId) {
        service.deleteBookedReservationAndAssociatedData(reservationId);
    }
}
