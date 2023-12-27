package pl.flywithbookedseats.api.passenger;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.flywithbookedseats.domain.user.User;
import pl.flywithbookedseats.domain.user.UserRole;

@Mapper(componentModel = "spring")
public interface RegisterUserCommandMapper {

    @Mapping(target = "role", qualifiedByName = "getUserRole")
    User toDomain(RegisterUserCommand command);

    @Named("getUserRole")
    default UserRole setUserRole() {
        return UserRole.USER;
    }
}
