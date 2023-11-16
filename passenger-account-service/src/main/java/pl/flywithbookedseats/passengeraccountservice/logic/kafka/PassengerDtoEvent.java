package pl.flywithbookedseats.passengeraccountservice.logic.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.flywithbookedseats.passengeraccountservice.logic.model.dto.PassengerAccountDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDtoEvent {

    private String message;
    private String status;
    private PassengerAccountDto passengerAccountDto;
}
