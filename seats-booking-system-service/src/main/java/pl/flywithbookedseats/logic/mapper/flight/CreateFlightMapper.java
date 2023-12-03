package pl.flywithbookedseats.logic.mapper.flight;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.logic.model.command.flight.CreateFlightCommand;
import pl.flywithbookedseats.logic.model.domain.Flight;

import java.util.function.Function;

@Component
public class CreateFlightMapper implements Function<CreateFlightCommand, Flight> {
    @Override
    public Flight apply(CreateFlightCommand createFlightCommand) {
        return Flight.builder()
                .flightServiceId(createFlightCommand.flightServiceId())
                .flightName(createFlightCommand.flightName())
                .planeTypeName(createFlightCommand.planeTypeName())
                .bookedSeatsInPlaneMap(createFlightCommand.bookedSeatsInPlaneList())
                .build();
    }
}
