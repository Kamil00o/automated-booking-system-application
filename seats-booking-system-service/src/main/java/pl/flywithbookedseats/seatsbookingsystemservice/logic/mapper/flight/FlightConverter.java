package pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.flight;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.PassengerNotFoundException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Passenger;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.PassengerRepository;

import java.util.Map;
import java.util.TreeMap;

import static pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.passenger.PassengerConstsImpl.PASSENGER_NOT_FOUND_ID;

@AllArgsConstructor
@Component
public class FlightConverter {

    private final PassengerRepository passengerRepository;
    public Map<String, String> convertBookedSeatsInPlaneMapToDtoVersion(Map<String, Long> bookedSeatsInPlaneMap) {
        Map<String, String> convertedBookedSeatsInPlaneMap = new TreeMap<String, String>();
        for (Map.Entry<String, Long> entry : bookedSeatsInPlaneMap.entrySet()) {
            convertedBookedSeatsInPlaneMap.put(entry.getKey(), retrievePassengerNameSurname(entry.getValue()));
        }
        return convertedBookedSeatsInPlaneMap;
    }

    private String retrievePassengerNameSurname(Long passengerId) {
        StringBuilder passengerNameSurname = new StringBuilder();
        if (passengerId != null && passengerId != 0L) {
            Passenger savedPassenger = retrievePassengerEntityFromDb(passengerId);
            passengerNameSurname.append(savedPassenger.getName())
                    .append(" ")
                    .append(savedPassenger.getSurname());
            return passengerNameSurname.toString();
        } else {
            passengerNameSurname.append("free");
            return passengerNameSurname.toString();
        }
    }

    private Passenger retrievePassengerEntityFromDb(Long passengerId) {
        return passengerRepository.findById(passengerId)
                .orElseThrow(() -> new PassengerNotFoundException(PASSENGER_NOT_FOUND_ID.formatted(passengerId)));
    }
}
