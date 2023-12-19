package pl.flywithbookedseats.domain.passenger;

import pl.flywithbookedseats.api.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.api.passenger.UpdatePassengerCommand;
import pl.flywithbookedseats.api.passenger.PassengerDto;

import java.util.List;

public interface PassengerService1 {

    PassengerDto createNewPassenger(CreatePassengerCommand createPassengerCommand);

    PassengerDto updatePassengerByEmail(UpdatePassengerCommand updatePassengerCommand, String email);

    PassengerDto retrievePassengerByEmail(String email);

    List<PassengerDto> retrieveAllPassengers();

    void deleteAllPassengers();

    void deletePassengerById(Long id);

    void deletePassengerByEmail(String email);
}
