package pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PagePassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PagePassenger;

@Mapper(componentModel = "spring")
public interface PagePassengerDtoMapper {

    PagePassengerAccountDto toDto(PagePassenger domain);

    PagePassenger toDomain(PagePassengerAccountDto dto);
}
