package pl.flywithbookedseats.api.seatsbookingsystem;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.appservices.SeatsBookingApplicationService;
import pl.flywithbookedseats.api.reservation.ReservationDto;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/seats-booking")
public class SeatsBookingSystemController {

    private final SeatsBookingApplicationService service;
    private final BookingEnterDataCommandMapper enterMapper;

    @PostMapping
    public ResponseEntity<ReservationDto> bookSeatsInThePlane(@Valid @RequestBody BookingEnterDataCommand bookingEnterDataCommand) {
        return ResponseEntity.ok(service.bookSeatsInThePlane(enterMapper.toDomain(bookingEnterDataCommand)));
    }

    @DeleteMapping(path = "/{reservationId}")
    public ResponseEntity<Void> deleteBookedReservationAndAssociatedData(@PathVariable Long reservationId) {
        service.deleteBookedReservationAndAssociatedData(reservationId);
        log.info("ReservationEntity and its data have been removed.");

        return ResponseEntity.ok().build();
    }
}
