package pl.flywithbookedseats.logic.mapper.flight;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.logic.model.domain.Flight;
import pl.flywithbookedseats.logic.model.dto.FlightDto;

import java.util.function.Function;

@AllArgsConstructor
@Component
public class FlightDtoMapper implements Function<Flight, FlightDto> {

    private final FlightConverter flightConverter;
    @Override
    public FlightDto apply(Flight flight) {
        return FlightDto.builder()
                .flightServiceId(flight.getFlightServiceId())
                .flightName(flight.getFlightName())
                .planeTypeName(flight.getPlaneTypeName())
                .bookedSeatsInPlaneList(flightConverter
                        .convertBookedSeatsInPlaneMapToDtoVersion(flight.getBookedSeatsInPlaneMap()))
                .build();
    }
}
