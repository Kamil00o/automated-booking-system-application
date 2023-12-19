package pl.flywithbookedseats.api.passeger;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.domain.passenger.PassengerBusinessLogic;
import pl.flywithbookedseats.external.storage.reservation.ReservationEntity;

import java.util.List;

@RequiredArgsConstructor
@Component
@Named("PassengerCommandsResolverMapper")
public class PassengerCommandsResolverMapper {

    private final PassengerBusinessLogic service;

    @Named("convertList")
    public List<ReservationEntity> convertList(List<Long> reservationIdList) {
        return service.parseReservationIdToReservationEntity(reservationIdList);
    }
}
