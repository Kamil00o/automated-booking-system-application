package pl.flywithbookedseats.passengeraccountservice.domain.passenger.service;

import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;

public interface PassengerAccountRepository {

    PassengerAccount save(PassengerAccount passengerAccount);

    PassengerAccount findById(Long id);
}
