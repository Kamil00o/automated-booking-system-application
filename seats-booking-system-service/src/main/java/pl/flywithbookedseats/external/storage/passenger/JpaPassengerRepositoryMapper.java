package pl.flywithbookedseats.external.storage.passenger;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.domain.passenger.Passenger;

@Mapper(componentModel = "spring")
public interface JpaPassengerRepositoryMapper {

    PassengerEntity toEntity(Passenger domain);

    Passenger toDomain(PassengerEntity entity);
}
