package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.passenger;

import jakarta.transaction.Transactional;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.passenger.UpdatePassengerCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.PassengerDto;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.PassengerService;

import java.util.List;

public class PassengerServiceImpl implements PassengerService {

    @Transactional
    @Override
    public PassengerDto createNewPassenger(CreatePassengerCommand createPassengerCommand) {
        return null;
    }

    @Transactional
    @Override
    public PassengerDto updatePassengerByEmail(UpdatePassengerCommand updatePassengerCommand) {
        return null;
    }

    @Override
    public PassengerDto retrievePassengerByEmail(String email) {
        return null;
    }

    @Override
    public List<PassengerDto> retrieveAllPassengers() {
        return null;
    }

    @Transactional
    @Override
    public void deleteAllPassengers() {

    }

    @Transactional
    @Override
    public void deletePassengerByEmail(String email) {

    }
}
