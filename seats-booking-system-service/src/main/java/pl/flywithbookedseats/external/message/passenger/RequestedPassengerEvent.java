package pl.flywithbookedseats.external.message.passenger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.flywithbookedseats.api.passenger.PassengerDto;
import pl.flywithbookedseats.external.message.passenger.KafkaPassengerDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestedPassengerEvent {

    private String message;
    private String messageSource;
    private String status;
    private KafkaPassengerDto passengerDto;
}
