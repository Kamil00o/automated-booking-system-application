package pl.flywithbookedseats.api.passenger;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.external.storage.reservation.ReservationEntity;
import pl.flywithbookedseats.external.storage.reservation.JpaReservationRepository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class PassengerConverter {

    private final JpaReservationRepository jpaReservationRepository;

    public List<ReservationEntity> convertReservationIdListToEntityVersion(List<Long> reservationIdList) {
        List<ReservationEntity> convertedReservationListEntity = new ArrayList<>();
        if (reservationIdList != null) {
            reservationIdList.forEach(id -> {
                ReservationEntity savedReservationEntity = retrieveReservationEntityFromDb(id);
                if (savedReservationEntity != null) {
                    convertedReservationListEntity.add(savedReservationEntity);
                }
            });
        }
        return convertedReservationListEntity;
    }

    private ReservationEntity retrieveReservationEntityFromDb(Long id) {
        return jpaReservationRepository.findById(id).orElse(null);
    }
}
