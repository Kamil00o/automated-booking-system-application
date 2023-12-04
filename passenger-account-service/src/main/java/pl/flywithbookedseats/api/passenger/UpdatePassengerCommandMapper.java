package pl.flywithbookedseats.api.passenger;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.domain.passenger.Passenger;

@Mapper(componentModel = "spring")
public interface UpdatePassengerCommandMapper {

    Passenger toDomain(UpdatePassengerCommand updateCommand);
}
