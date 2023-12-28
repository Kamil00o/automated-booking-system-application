package pl.flywithbookedseats.api.passenger;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.UserRole;

@Mapper(componentModel = "spring")
public interface UpdatePassengerCommandMapper {

    @Mapping(source = "updateCommand", target = "role", qualifiedByName = "getUserRole")
    Passenger toDomain(UpdatePassengerCommand updateCommand);

    @Named("getUserRole")
    default UserRole setUserRole(UpdatePassengerCommand updateCommand) {
        return UserRole.USER;
    }
}
