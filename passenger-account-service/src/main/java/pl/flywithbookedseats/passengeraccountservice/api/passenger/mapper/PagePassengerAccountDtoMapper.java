package pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PagePassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PagePassengerAccount;

@Mapper(componentModel = "spring")
public interface PagePassengerAccountDtoMapper {

    PagePassengerAccountDto toDto(PagePassengerAccount domain);

    PagePassengerAccount toDomain(PagePassengerAccountDto dto);
}
