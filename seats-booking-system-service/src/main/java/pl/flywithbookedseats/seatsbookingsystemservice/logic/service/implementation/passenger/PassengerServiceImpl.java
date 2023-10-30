package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.passenger;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.passenger.UpdatePassengerCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.PassengerDto;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.PassengerRepository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.PassengerService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;

    @Transactional
    @Override
    public PassengerDto createNewPassenger(CreatePassengerCommand createPassengerCommand) {
        if (exists(createPassengerCommand)) {
            //TODO: RESERVATION DOMAIN NEED TO BE DONE FIRST
        }
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

    private boolean exists(CreatePassengerCommand createPassengerCommand) {
        return passengerRepository.existsByEmail(createPassengerCommand.email());
    }
}
