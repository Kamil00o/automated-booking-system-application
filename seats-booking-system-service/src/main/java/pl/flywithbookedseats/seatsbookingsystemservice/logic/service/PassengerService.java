package pl.flywithbookedseats.seatsbookingsystemservice.logic.service;

import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.passenger.UpdatePassengerCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.PassengerDto;

import java.util.List;

public interface PassengerService {

    PassengerDto createNewPassenger(CreatePassengerCommand createPassengerCommand);

    List<PassengerDto> updatePassengerByEmail(UpdatePassengerCommand updatePassengerCommand);

    PassengerDto retrievePassengerByEmail(String email);

    void deleteAllPassengers();

    void deletePassengerByEmail();
}
