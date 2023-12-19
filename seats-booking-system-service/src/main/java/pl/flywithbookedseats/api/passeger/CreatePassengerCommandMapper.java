package pl.flywithbookedseats.api.passeger;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.flywithbookedseats.domain.passenger.Passenger;

@Mapper(componentModel = "spring", uses = {PassengerCommandsResolverMapper.class})
public interface CreatePassengerCommandMapper {

    @Mapping(
            source = "reservationsIdList",
            target = "reservationsList",
            qualifiedByName = {"PassengerCommandsResolverMapper", "convertList"}
    )
    Passenger toDomain(CreatePassengerCommand command);

}
