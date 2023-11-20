package pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;

@Mapper(componentModel = "spring")
public interface PassengerAccountDtoMapper {

    PassengerAccountDto toDto(PassengerAccount domain);

    PassengerAccount toDomain(PassengerAccountDto dto);
}
