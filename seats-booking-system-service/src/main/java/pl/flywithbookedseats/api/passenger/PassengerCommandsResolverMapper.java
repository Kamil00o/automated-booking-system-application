package pl.flywithbookedseats.api.passenger;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.domain.reservation.Reservation;
import pl.flywithbookedseats.domain.reservation.ReservationService;

import java.util.List;

@RequiredArgsConstructor
@Component
@Named("PassengerCommandsResolverMapper")
public class PassengerCommandsResolverMapper {

    private final ReservationService service;

    @Named("convertList")
    public List<Reservation> convertList(List<Long> reservationIdList) {
        return service.parseReservationIdToReservationEntity(reservationIdList);
    }
}
