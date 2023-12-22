package pl.flywithbookedseats.external.message.passenger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.flywithbookedseats.api.passenger.PassengerDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedPassengerEvent {

    private String message;
    private String messageSource;
    private String status;
    private KafkaPassengerDto passengerDto;
}
