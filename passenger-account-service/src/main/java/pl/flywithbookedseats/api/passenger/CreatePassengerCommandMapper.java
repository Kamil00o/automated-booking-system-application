package pl.flywithbookedseats.api.passenger;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.domain.passenger.Passenger;

@Mapper(componentModel = "spring")
public interface CreatePassengerCommandMapper {

    Passenger toDomain(CreatePassengerCommand createCommand);

}
