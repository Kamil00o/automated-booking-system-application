package pl.flywithbookedseats.logic.mapper.flight;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.logic.model.domain.Passenger;
import pl.flywithbookedseats.logic.repository.PassengerRepository;

import java.util.Map;
import java.util.TreeMap;

import static pl.flywithbookedseats.common.Consts.SEAT_PASSENGER_DATA_UNAVAILABLE;

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
            if (savedPassenger != null) {
                passengerNameSurname.append(savedPassenger.getName())
                        .append(" ")
                        .append(savedPassenger.getSurname());
            } else {
                passengerNameSurname.append(SEAT_PASSENGER_DATA_UNAVAILABLE);
            }

            return passengerNameSurname.toString();
        } else {
            passengerNameSurname.append("free");
            return passengerNameSurname.toString();
        }
    }

    private Passenger retrievePassengerEntityFromDb(Long passengerId) {
        return passengerRepository.findById(passengerId).orElse(null);
    }
}
