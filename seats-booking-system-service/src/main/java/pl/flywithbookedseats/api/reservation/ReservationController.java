package pl.flywithbookedseats.api.reservation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.appservices.ReservationApplicationService;
import pl.flywithbookedseats.domain.reservation.PageReservation;
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
    private final UpdateReservationCommandMapper updateMapper;
    private final PageReservationDtoMapper pageMapper;


    @PostMapping
    public ReservationDto addNewReservationToDb(@Valid @RequestBody CreateReservationCommand createReservationCommand) {
        log.info("Adding new reservation for {} flight to database", createReservationCommand.flightNumber());
        Reservation createdReservation = service.addNewReservationToDb(createMapper.toDomain(createReservationCommand));

        return mapper.toDto(createdReservation);
    }

    @PutMapping("/{id}")
    public ReservationDto updateReservationById(@Valid @RequestBody UpdateReservationCommand updateReservationCommand,
                                                @PathVariable Long id) {
        Reservation updatedReservation = service
                .updateReservationById(updateMapper.toDomain(updateReservationCommand), id);

        return mapper.toDto(updatedReservation);
    }

    @GetMapping
    public PageReservationDto retrieveAllReservations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageReservation pageReservation = service.retrieveAllReservations(pageable);

        return pageMapper.toDto(pageReservation);
    }

    @GetMapping(path = "/id/{id}")
    public ReservationDto retrieveReservationById(@PathVariable Long id) {
        log.info("Retrieving reservation for ID: {}:", id);
        Reservation savedReservation = service.retrieveReservationById(id);

        return mapper.toDto(savedReservation);
    }

    @GetMapping(path = "/get-reservation/email/{email}")
    public List<ReservationDto> retrieveReservationByEmail(@PathVariable String email) {
        log.info("Retrieving reservation for email: {}:", email);
        return reservationService.retrieveReservationByEmail(email);
    }

    @DeleteMapping
    public void deleteAllReservations() {
        log.info("Removing all reservations:");
        service.deleteAllReservations();
        log.info(REMOVING_RESERVATION_COMPLETE);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteReservationById(@PathVariable Long id) {
        log.info("Removing reservation for ID: {}:", id);
        service.deleteReservationById(id);
        log.info(REMOVING_RESERVATION_COMPLETE);
    }
}
