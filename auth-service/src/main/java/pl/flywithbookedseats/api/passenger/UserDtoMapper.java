package pl.flywithbookedseats.api.passenger;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.domain.user.User;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    User toDomain(UserDto dto);

    UserDto toDto(User domain);
}
