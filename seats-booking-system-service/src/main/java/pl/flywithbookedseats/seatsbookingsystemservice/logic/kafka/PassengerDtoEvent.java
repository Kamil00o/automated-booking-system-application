package pl.flywithbookedseats.seatsbookingsystemservice.logic.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.PassengerDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDtoEvent {

    private String message;
    private String status;
    private PassengerDto passengerDto;
}