package pl.flywithbookedseats.external.storage.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.flywithbookedseats.domain.reservation.Reservation;
import pl.flywithbookedseats.domain.reservation.ReservationRepository;

@Slf4j
@RequiredArgsConstructor
public class ReservationAdapterRepository implements ReservationRepository {

    private final JpaReservationRepositoryMapper mapper;
    private final JpaReservationRepository repository;

    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity savedReservationEntity = repository.save(mapper.toEntityy(reservation));
        return mapper.toDomain(savedReservationEntity);
    }

    @Override
    public boolean existsBySeatNumber(String seatNumber) {
        return repository.existsBySeatNumber(seatNumber);
    }
}
