package pl.flywithbookedseats.logic.service;

import pl.flywithbookedseats.logic.model.command.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.logic.model.command.passenger.UpdatePassengerCommand;
import pl.flywithbookedseats.logic.model.dto.PassengerDto;

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