package pl.flywithbookedseats.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.flywithbookedseats.api.passenger.PassengerDto;
import pl.flywithbookedseats.external.message.passenger.RequestType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestedPassengerEvent {

    private String message;
    private String messageSource;
    private String status;
    private PassengerDto passengerDto;
}
