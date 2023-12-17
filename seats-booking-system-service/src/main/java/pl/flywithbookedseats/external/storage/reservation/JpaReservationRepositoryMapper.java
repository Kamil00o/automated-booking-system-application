package pl.flywithbookedseats.external.storage.reservation;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.domain.reservation.Reservation;

@Mapper(componentModel = "spring")
public interface JpaReservationRepositoryMapper {

    ReservationEntity toEntityy(Reservation domain);

    Reservation toDomain(ReservationEntity entity);

}
