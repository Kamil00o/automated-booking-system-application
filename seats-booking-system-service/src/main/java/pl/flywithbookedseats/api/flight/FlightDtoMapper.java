package pl.flywithbookedseats.api.flight;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.flywithbookedseats.domain.flight.Flight;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface FlightDtoMapper {

    @Mapping(source = "bookedSeatsInPlaneMapDtoVersion", target = "bookedSeatsInPlaneList")
    FlightDto toDto(Flight domain, Map<String, String> bookedSeatsInPlaneMapDtoVersion);

}
