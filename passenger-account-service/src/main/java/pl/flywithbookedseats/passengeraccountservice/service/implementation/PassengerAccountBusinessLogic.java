package pl.flywithbookedseats.passengeraccountservice.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.passengeraccountservice.exceptions.PassengerAccountNotFoundException;
import pl.flywithbookedseats.passengeraccountservice.model.command.CreatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.command.UpdatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.domain.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.repository.PassengerAccountRepository;

import static pl.flywithbookedseats.passengeraccountservice.service.implementation.PassengerAccountConsts.*;

@AllArgsConstructor
@Component
public class PassengerAccountBusinessLogic {

    private final PassengerAccountRepository passengerAccountRepository;

    public PassengerAccount retrievePassengerAccountFromDb(Long id) {
        return passengerAccountRepository.findById(id)
                .orElseThrow(() -> new PassengerAccountNotFoundException(PASSENGER_ACCOUNT_NOT_FOUND_ID
                        .formatted(id)));
    }
    public PassengerAccount retrievePassengerAccountFromDb(String email) {
        return passengerAccountRepository.findByEmail(email)
                .orElseThrow(() -> new PassengerAccountNotFoundException(PASSENGER_ACCOUNT_NOT_FOUND_EMAIL
                        .formatted(email)));
    }

    public boolean exists(CreatePassengerAccount createPassengerAccount) {
        return passengerAccountRepository.existsByEmail(createPassengerAccount.email());
    }

    public boolean exists(UpdatePassengerAccount updatePassengerAccount) {
        return passengerAccountRepository.existsByEmail(updatePassengerAccount.email());
    }
}
