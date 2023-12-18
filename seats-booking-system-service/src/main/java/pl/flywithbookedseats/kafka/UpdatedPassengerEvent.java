package pl.flywithbookedseats.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.flywithbookedseats.api.passeger.PassengerDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedPassengerEvent {

    private String message;
    private String messageSource;
    private String status;
    private PassengerDto passengerDto;
}
