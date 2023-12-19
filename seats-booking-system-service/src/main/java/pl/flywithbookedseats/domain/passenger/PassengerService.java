package pl.flywithbookedseats.domain.passenger;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.flywithbookedseats.api.passeger.PassengerDto;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository repository;

    public Passenger createNewPassenger(Passenger passenger) {
        return null;
    }

    public Passenger updatePassengerByEmail(Passenger passenger, String email) {
        return null;
    }

    public Passenger retrievePassengerByEmail(String email) {
        return null;
    }

    public Passenger retrievePassengerById(Long id) {
        return null;
    }

    public List<PassengerDto> retrieveAllPassengers() {
        return null;
    }

    public void deleteAllPassengers() {

    }

    public void deletePassengerById(Long id) {

    }

    public void deletePassengerByEmail(String email) {

    }
}
