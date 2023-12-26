package pl.flywithbookedseats.external.storage.reservation;

import org.mapstruct.*;
import pl.flywithbookedseats.domain.reservation.Reservation;
import pl.flywithbookedseats.external.storage.passenger.JpaPassengerRepositoryMapper;

@Named("JpaReservationRepositoryMapper")
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {JpaPassengerRepositoryMapper.class}
        )
public interface JpaReservationRepositoryMapper {

    @Mapping(source = "passenger", target = "passengerEntity")
    ReservationEntity toEntity(Reservation domain);

    @Mappings({
            @Mapping(source = "passengerEntity", target = "passenger", qualifiedByName = {
                    "JpaPassengerRepositoryMapper", "toDomainWithoutReservation"
            })
    })
    Reservation toDomain(ReservationEntity entity);

}
