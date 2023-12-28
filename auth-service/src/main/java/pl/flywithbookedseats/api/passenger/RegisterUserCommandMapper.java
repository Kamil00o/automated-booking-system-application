package pl.flywithbookedseats.api.passenger;

import org.mapstruct.*;
import pl.flywithbookedseats.domain.user.User;
import pl.flywithbookedseats.domain.user.UserRole;

@Mapper(componentModel = "spring")
public interface RegisterUserCommandMapper {

    @Mapping(source = "command", target = "role", qualifiedByName = "getUserRole")
    User toDomain(RegisterUserCommand command);

    @Named("getUserRole")
    default UserRole setUserRole(RegisterUserCommand command) {
        return UserRole.USER;
    }
}
