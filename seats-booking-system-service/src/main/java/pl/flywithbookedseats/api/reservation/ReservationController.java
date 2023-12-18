package pl.flywithbookedseats.api.reservation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.appservices.ReservationApplicationService;
import pl.flywithbookedseats.domain.reservation.PageReservation;
import pl.flywithbookedseats.domain.reservation.Reservation;

import java.util.List;
import java.util.stream.Collectors;

import static pl.flywithbookedseats.domain.reservation.ReservationConstsImpl.REMOVING_RESERVATION_COMPLETE;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/seats-booking/reservation")
public class ReservationController {

    private final ReservationApplicationService service;
    private final CreateReservationCommandMapper createMapper;
    private final ReservationDtoMapper mapper;
    private final UpdateReservationCommandMapper updateMapper;
    private final PageReservationDtoMapper pageMapper;


    @PostMapping
    public ResponseEntity<ReservationDto> addNewReservationToDb(@Valid @RequestBody CreateReservationCommand createReservationCommand) {
        log.info("Adding new reservation for {} flight to database", createReservationCommand.flightNumber());
        Reservation createdReservation = service.addNewReservationToDb(createMapper.toDomain(createReservationCommand));

        return ResponseEntity.ok(mapper.toDto(createdReservation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDto> updateReservationById(@Valid @RequestBody UpdateReservationCommand updateReservationCommand,
                                                @PathVariable Long id) {
        Reservation updatedReservation = service
                .updateReservationById(updateMapper.toDomain(updateReservationCommand), id);

        return ResponseEntity.ok(mapper.toDto(updatedReservation));
    }

    @GetMapping
    public ResponseEntity<PageReservationDto> retrieveAllReservations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageReservation pageReservation = service.retrieveAllReservations(pageable);

        return ResponseEntity.ok(pageMapper.toDto(pageReservation));
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<ReservationDto> retrieveReservationById(@PathVariable Long id) {
        log.info("Retrieving reservation for ID: {}:", id);
        Reservation savedReservation = service.retrieveReservationById(id);

        return ResponseEntity.ok(mapper.toDto(savedReservation));
    }

    @GetMapping(path = "/email/{email}")
    public ResponseEntity<List<ReservationDto>> retrieveReservationByEmail(@PathVariable String email) {
        log.info("Retrieving reservation for email: {}:", email);
        List<Reservation> reservationList = service.retrieveReservationByEmail(email);

        return ResponseEntity.ok(reservationList.stream().map(mapper::toDto).collect(Collectors.toList()));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllReservations() {
        log.info("Removing all reservations:");
        service.deleteAllReservations();
        log.info(REMOVING_RESERVATION_COMPLETE);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteReservationById(@PathVariable Long id) {
        log.info("Removing reservation for ID: {}:", id);
        service.deleteReservationById(id);
        log.info(REMOVING_RESERVATION_COMPLETE);

        return ResponseEntity.ok().build();
    }
}
