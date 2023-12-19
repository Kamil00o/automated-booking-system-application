package pl.flywithbookedseats.appservices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.api.passeger.CreatePassengerCommand;
import pl.flywithbookedseats.api.passeger.PassengerDto;
import pl.flywithbookedseats.api.passeger.UpdatePassengerCommand;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.external.storage.passenger.PassengerEntity;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PassengerApplicationService {

    @Transactional
    public Passenger createNewPassenger(Passenger passenger) {
        return null;
    }

    @Transactional
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

    @Transactional
    public void deleteAllPassengers() {

    }

    @Transactional
    public void deletePassengerById(Long id) {

    }

    @Transactional
    public void deletePassengerByEmail(String email) {

    }
}
