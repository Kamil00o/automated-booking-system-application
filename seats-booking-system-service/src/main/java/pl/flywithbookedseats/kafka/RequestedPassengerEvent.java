package pl.flywithbookedseats.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.flywithbookedseats.logic.model.dto.PassengerDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestedPassengerEvent {

    private String message;
    private String messageSource;
    private String status;
    private PassengerDto passengerDto;
}
