package pl.flywithbookedseats.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.flywithbookedseats.logic.model.dto.PassengerDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDtoEvent {

    private String message;
    private String status;
    private String requestType;
    private PassengerDto passengerDto;
}
