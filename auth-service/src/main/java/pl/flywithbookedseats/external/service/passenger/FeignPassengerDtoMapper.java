package pl.flywithbookedseats.external.service.passenger;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.domain.user.User;

@Mapper(componentModel = "spring")
public interface FeignPassengerDtoMapper {

    FeignPassengerDto toDto(User domain);

    User toDomain(FeignPassengerDto dto);
}
