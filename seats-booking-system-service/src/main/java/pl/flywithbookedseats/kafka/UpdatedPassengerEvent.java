package pl.flywithbookedseats.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.flywithbookedseats.api.passenger.PassengerDto;
import pl.flywithbookedseats.external.message.passenger.KafkaPassengerDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedPassengerEvent {

    private String message;
    private String messageSource;
    private String status;
    private KafkaPassengerDto passengerDto;
}
