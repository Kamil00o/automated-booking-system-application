package pl.flywithbookedseats.external.storage.reservation;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.flywithbookedseats.domain.reservation.Reservation;

@Mapper(componentModel = "spring")
public interface JpaReservationRepositoryMapper {

    @Mapping(source = "passenger", target = "passengerEntity")
    ReservationEntity toEntityy(Reservation domain);

    @Mapping(source = "passengerEntity", target = "passenger")
    Reservation toDomain(ReservationEntity entity);

}
