package pl.flywithbookedseats.api.seatsbookingsystem;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.domain.seatsbookingsystem.SeatsBookingServiceImpl;
import pl.flywithbookedseats.logic.model.dto.ReservationDto;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/seats-booking")
public class SeatsBookingSystemController {

    private final SeatsBookingServiceImpl bookingService;

    @PostMapping
    public ReservationDto bookSeatsInThePlane(@Valid @RequestBody BookingEnterDataCommand bookingEnterDataCommand) {
        return bookingService.bookSeatsInThePlane(bookingEnterDataCommand);
    }

    @DeleteMapping(path = "/{reservationId}")
    public void deleteBookedReservationAndAssociatedData(@PathVariable Long reservationId) {
        bookingService.deleteBookedReservationAndAssociatedData(reservationId);
        log.info("Reservation and its data have been removed.");
    }
}
