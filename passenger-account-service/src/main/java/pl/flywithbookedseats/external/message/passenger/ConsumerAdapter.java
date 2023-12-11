package pl.flywithbookedseats.external.message.passenger;

import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.api.passenger.PassengerDtoMapper;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.PassengerService;
import pl.flywithbookedseats.kafka.RequestedPassengerEvent;
import pl.flywithbookedseats.kafka.UpdatedPassengerEvent;

import static pl.flywithbookedseats.common.Consts.REQUEST_TYPE_IN_MSG_NOT_SUPPORTED;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConsumerAdapter {

    private final PassengerDtoMapper mapper;
    private final PassengerService service;

    public void consumeRequestedPassengerEvent(RequestedPassengerEvent requestedPassengerEvent) {
        Passenger receivedPassenger = mapper.toDomain(requestedPassengerEvent.getPassengerDto());
        service.handlePassengerDataRequest(receivedPassenger);
    }

    public void consumeUpdatePassengerEvent(UpdatedPassengerEvent updatedPassengerEvent) {
        Passenger receivedPassenger = mapper.toDomain(updatedPassengerEvent.getPassengerDto());
        service.updatePassengerAccountByEmail(receivedPassenger.getEmail(), receivedPassenger);
    }
}
