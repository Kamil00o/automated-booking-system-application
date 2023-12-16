package pl.flywithbookedseats.api.flight;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.domain.flight.Flight;

@Mapper(componentModel = "spring")
public interface CreateFlightCommandMapper {

    Flight toDomain(CreateFlightCommand command);

}
