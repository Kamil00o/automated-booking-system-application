package pl.flywithbookedseats.logic.mapper.passenger;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.logic.model.domain.Reservation;
import pl.flywithbookedseats.logic.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class PassengerConverter {

    private final ReservationRepository reservationRepository;

    public List<Reservation> convertReservationIdListToEntityVersion(List<Long> reservationIdList) {
        List<Reservation> convertedReservationList = new ArrayList<>();
        if (reservationIdList != null) {
            reservationIdList.forEach(id -> {
                Reservation savedReservation = retrieveReservationEntityFromDb(id);
                if (savedReservation != null) {
                    convertedReservationList.add(savedReservation);
                }
            });
        }
        return convertedReservationList;
    }

    private Reservation retrieveReservationEntityFromDb(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }
}
