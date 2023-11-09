package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.seatsbookingsystem;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.BookingEnterDataCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.ReservationDto;

@RequiredArgsConstructor
@Service
public class SeatsBookingServiceImpl {

    private final SeatsBookingBusinessLogic seatsBookingBL;

    @Transactional
    public ReservationDto bookSeatsInThePlane(BookingEnterDataCommand bookingEnterDataCommand) {
        return seatsBookingBL.bookSeatsInThePlane(bookingEnterDataCommand);
    }
}
