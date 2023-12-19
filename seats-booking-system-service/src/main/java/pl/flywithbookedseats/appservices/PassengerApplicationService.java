package pl.flywithbookedseats.appservices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.api.passenger.PassengerDto;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.PassengerService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PassengerApplicationService {

    private final PassengerService service;

    @Transactional
    public Passenger createNewPassenger(Passenger passenger) {
        return service.createNewPassenger(passenger);
    }

    @Transactional
    public Passenger updatePassengerByEmail(Passenger passenger, String email) {
        return null;
    }

    public Passenger retrievePassengerByEmail(String email) {
        return service.retrievePassengerByEmail(email);
    }

    public Passenger retrievePassengerById(Long id) {
        return service.retrievePassengerById(id);
    }

    public Passenger retrievePassengerByPassengerServiceId(Long id) {
        return service.retrievePassengerByPassengerServiceId(id);
    }

    public List<PassengerDto> retrieveAllPassengers() {
        return null;
    }

    @Transactional
    public void deleteAllPassengers() {
        service.deleteAllPassengers();
    }

    @Transactional
    public void deletePassengerById(Long id) {
        service.deletePassengerById(id);
    }

    @Transactional
    public void deletePassengerByEmail(String email) {
        service.deletePassengerByEmail(email);
    }
}
