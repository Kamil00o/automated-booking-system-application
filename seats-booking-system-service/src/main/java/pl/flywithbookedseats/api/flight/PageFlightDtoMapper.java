package pl.flywithbookedseats.api.flight;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.domain.flight.PageFlight;

@Mapper(componentModel = "spring")
public interface PageFlightDtoMapper {

    PageFlightDto toDto(PageFlight domain);
}
