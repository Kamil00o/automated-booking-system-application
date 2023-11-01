package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.passenger;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.PassengerNotFoundException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.passenger.PassengerDtoMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.passenger.UpdatePassengerCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Passenger;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.PassengerDto;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.PassengerRepository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.PassengerService;

import static pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.passenger.PassengerConstsImpl.*;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerDtoMapper passengerDtoMapper;

    @Transactional
    @Override
    public PassengerDto createNewPassenger(CreatePassengerCommand createPassengerCommand) {
        if (exists(createPassengerCommand)) {

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
        Passenger savedPassenger = passengerRepository.findByEmail(email)
                .orElseThrow(() -> new PassengerNotFoundException(PASSENGER_NOT_FOUND_EMAIL.formatted(email)));

        return passengerDtoMapper.apply(savedPassenger);
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

    public Passenger getPassengerByEmail(String email) {
        Passenger savedPassenger = passengerRepository.findByEmail(email)
                .orElseThrow(() -> new PassengerNotFoundException(PASSENGER_NOT_FOUND_EMAIL.formatted(email)));

        return savedPassenger;
    }

    public boolean exists(String email) {
        return passengerRepository.existsByEmail(email);
    }

    private boolean exists(CreatePassengerCommand createPassengerCommand) {
        return passengerRepository.existsByEmail(createPassengerCommand.email());
    }
}
