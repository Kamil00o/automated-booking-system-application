package pl.flywithbookedseats.domain.passenger;

import pl.flywithbookedseats.api.passeger.CreatePassengerCommand;
import pl.flywithbookedseats.api.passeger.UpdatePassengerCommand;
import pl.flywithbookedseats.api.passeger.PassengerDto;

import java.util.List;

public interface PassengerService {

    PassengerDto createNewPassenger(CreatePassengerCommand createPassengerCommand);

    PassengerDto updatePassengerByEmail(UpdatePassengerCommand updatePassengerCommand, String email);

    PassengerDto retrievePassengerByEmail(String email);

    List<PassengerDto> retrieveAllPassengers();

    void deleteAllPassengers();

    void deletePassengerById(Long id);

    void deletePassengerByEmail(String email);
}
