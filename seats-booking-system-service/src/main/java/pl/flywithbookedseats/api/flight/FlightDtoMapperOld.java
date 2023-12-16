package pl.flywithbookedseats.api.flight;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.external.storage.flight.FlightEntity;

import java.util.function.Function;

@AllArgsConstructor
@Component
public class FlightDtoMapperOld implements Function<FlightEntity, FlightDto> {

    private final FlightConverter flightConverter;
    @Override
    public FlightDto apply(FlightEntity flightEntity) {
        return FlightDto.builder()
                .flightServiceId(flightEntity.getFlightServiceId())
                .flightName(flightEntity.getFlightName())
                .planeTypeName(flightEntity.getPlaneTypeName())
                .bookedSeatsInPlaneList(flightConverter
                        .convertBookedSeatsInPlaneMapToDtoVersion(flightEntity.getBookedSeatsInPlaneMap()))
                .build();
    }
}
