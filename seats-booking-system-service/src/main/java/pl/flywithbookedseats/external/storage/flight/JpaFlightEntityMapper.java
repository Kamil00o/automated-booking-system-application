package pl.flywithbookedseats.external.storage.flight;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.domain.flight.Flight;

@Mapper(componentModel = "spring")
public interface JpaFlightEntityMapper {

    FlightEntity toEntity(Flight flight);

    Flight toDomain(FlightEntity entity);

}
