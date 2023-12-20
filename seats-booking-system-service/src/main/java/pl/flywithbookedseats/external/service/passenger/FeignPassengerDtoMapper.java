package pl.flywithbookedseats.external.service.passenger;

import org.mapstruct.Mapper;
import org.springframework.http.ResponseEntity;
import pl.flywithbookedseats.domain.passenger.Passenger;

@Mapper(componentModel = "spring")
public interface FeignPassengerDtoMapper {

    Passenger toDomain(ResponseEntity<FeignPassengerDto> dto);

}
