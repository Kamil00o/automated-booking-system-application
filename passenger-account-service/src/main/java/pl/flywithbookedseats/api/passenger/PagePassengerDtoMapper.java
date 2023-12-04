package pl.flywithbookedseats.api.passenger;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.domain.passenger.PagePassenger;

@Mapper(componentModel = "spring")
public interface PagePassengerDtoMapper {

    PagePassengerAccountDto toDto(PagePassenger domain);

    PagePassenger toDomain(PagePassengerAccountDto dto);
}
