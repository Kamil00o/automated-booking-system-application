package pl.flywithbookedseats.api.passenger;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.domain.user.User;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserDto toDto(User domain);

}
