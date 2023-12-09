package pl.flywithbookedseats.external.message.passenger;

import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.api.passenger.PassengerDtoMapper;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.PassengerService;
import pl.flywithbookedseats.kafka.PassengerDtoEvent;

import static pl.flywithbookedseats.common.Consts.REQUEST_TYPE_IN_MSG_NOT_SUPPORTED;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConsumerAdapter {

    private final PassengerDtoMapper mapper;
    private final PassengerService service;

    public void consumeMessage(PassengerDtoEvent passengerDtoEvent) {
        Passenger receivedPassenger = mapper.toDomain(passengerDtoEvent.getPassengerDto());
        RequestType receivedRequestType = passengerDtoEvent.getRequestType();
        if (receivedRequestType == RequestType.UPDATE) {
            service.updatePassengerAccountByEmail(receivedPassenger.getEmail(), receivedPassenger);
        } else if (receivedRequestType == RequestType.DATA_REQUEST) {
            service.handlePassengerDataRequest(receivedPassenger);
        } else {
            log.warn("Bad tequest type in message!");
            throw new BadRequestException(REQUEST_TYPE_IN_MSG_NOT_SUPPORTED);
        }
    }
}
