package pl.flywithbookedseats.external.service.passenger;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.flywithbookedseats.domain.passenger.Passenger;

@Mapper(componentModel = "spring")
public interface FeignPassengerDtoMapper {

    @Mapping(target = "nationality", ignore = true)
    @Mapping(target = "gender", ignore = true)
    Passenger toDomain(FeignPassengerDto dto);

}
