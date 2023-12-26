package pl.flywithbookedseats.external.storage.passenger;

import org.mapstruct.*;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.external.storage.reservation.ReservationEntity;

import java.util.ArrayList;
import java.util.List;

@Named("JpaPassengerRepositoryMapper")
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JpaPassengerRepositoryMapper {

    PassengerEntity toEntity(Passenger domain);

    Passenger toDomain(PassengerEntity entity);

    @Named("toDomainWithoutReservation")
    @Mappings({
            @Mapping(target = "reservationsList", expression = "java(null)")
    })
    Passenger toDomainWithoutReservation(PassengerEntity entity);
}
