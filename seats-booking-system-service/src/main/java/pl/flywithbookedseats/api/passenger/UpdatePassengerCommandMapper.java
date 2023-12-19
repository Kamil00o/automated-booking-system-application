package pl.flywithbookedseats.api.passenger;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.flywithbookedseats.domain.passenger.Passenger;

@Mapper(componentModel = "spring", uses = {PassengerCommandsResolverMapper.class})
public interface UpdatePassengerCommandMapper {

    @Mapping(
            source = "reservationsIdList",
            target = "reservationsList",
            qualifiedByName = {"PassengerCommandsResolverMapper", "convertList"}
    )
    Passenger toDomain(UpdatePassengerCommand command);

}
