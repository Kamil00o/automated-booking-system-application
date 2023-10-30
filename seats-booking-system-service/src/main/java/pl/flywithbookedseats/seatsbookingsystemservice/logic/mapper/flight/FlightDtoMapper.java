package pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.flight;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Flight;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.FlightDto;

import java.util.function.Function;

@Component
public class FlightDtoMapper implements Function<Flight, FlightDto> {
    @Override
    public FlightDto apply(Flight flight) {
        return FlightDto.builder()
                .flightServiceId(flight.getFlightServiceId())
                .flightName(flight.getFlightName())
                .planeTypeName(flight.getPlaneTypeName())
                .bookedSeatsInPlaneList(flight.getBookedSeatsInPlaneMap())
                .build();
    }
}
