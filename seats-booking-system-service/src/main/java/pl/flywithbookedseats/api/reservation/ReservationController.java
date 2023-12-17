package pl.flywithbookedseats.api.reservation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.appservices.ReservationApplicationService;
import pl.flywithbookedseats.domain.reservation.Reservation;
import pl.flywithbookedseats.domain.reservation.ReservationService1Impl;

import java.util.List;

import static pl.flywithbookedseats.domain.reservation.ReservationConstsImpl.REMOVING_RESERVATION_COMPLETE;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/seats-booking/reservation")
public class ReservationController {

    private final ReservationService1Impl reservationService;
    private final ReservationApplicationService service;
    private final CreateReservationCommandMapper createMapper;
    private final ReservationDtoMapper mapper;


    @PostMapping
    public ReservationDto addNewReservationToDb(@Valid @RequestBody CreateReservationCommand createReservationCommand) {
        log.info("Adding new reservation for {} flight to database", createReservationCommand.flightNumber());
        Reservation createdReservation = service.addNewReservationToDb(createMapper.toDomain(createReservationCommand));

        return mapper.toDto(createdReservation);
    }

    @PutMapping(path = "/update-reservation/id/{id}")
    public ReservationDto updateReservationById(@Valid @RequestBody UpdateReservationCommand updateReservationCommand,
                                                @PathVariable Long id) {
        return reservationService.updateReservationById(updateReservationCommand, id);
    }

    @GetMapping(path = "/get-reservation/all")
    public List<ReservationDto> retrieveAllReservations() {
        return reservationService.retrieveAllReservations();
    }

    @GetMapping(path = "/get-reservation/id/{id}")
    public ReservationDto retrieveReservationById(@PathVariable Long id) {
        log.info("Retrieving reservation for ID: {}:", id);
        return reservationService.retrieveReservationById(id);
    }

    @GetMapping(path = "/get-reservation/email/{email}")
    public List<ReservationDto> retrieveReservationByEmail(@PathVariable String email) {
        log.info("Retrieving reservation for email: {}:", email);
        return reservationService.retrieveReservationByEmail(email);
    }

    @DeleteMapping(path = "delete-reservation/all")
    public void deleteAllReservations() {
        log.info("Removing all reservations:");
        reservationService.deleteAllReservations();
        log.info(REMOVING_RESERVATION_COMPLETE);
    }

    @DeleteMapping(path = "delete-reservation/id/{id}")
    public void deleteReservationById(@PathVariable Long id) {
        log.info("Removing reservation for ID: {}:", id);
        reservationService.deleteReservationById(id);
        log.info(REMOVING_RESERVATION_COMPLETE);
    }
}
