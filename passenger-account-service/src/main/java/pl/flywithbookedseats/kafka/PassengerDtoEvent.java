package pl.flywithbookedseats.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.flywithbookedseats.api.passenger.PassengerDto;
import pl.flywithbookedseats.external.message.passenger.RequestType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDtoEvent {

    private String message;
    private String status;
    private RequestType requestType;
    private PassengerDto passengerDto;
}
