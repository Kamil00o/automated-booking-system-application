package pl.flywithbookedseats.api.flight;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.external.storage.flight.FlightEntity;

import java.util.function.Function;

@Component
public class CreateFlightMapper implements Function<CreateFlightCommand, FlightEntity> {
    @Override
    public FlightEntity apply(CreateFlightCommand createFlightCommand) {
        return FlightEntity.builder()
                .flightServiceId(createFlightCommand.flightServiceId())
                .flightName(createFlightCommand.flightName())
                .planeTypeName(createFlightCommand.planeTypeName())
                .bookedSeatsInPlaneMap(createFlightCommand.bookedSeatsInPlaneList())
                .build();
    }
}
