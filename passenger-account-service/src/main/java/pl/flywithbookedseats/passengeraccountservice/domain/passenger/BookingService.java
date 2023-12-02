package pl.flywithbookedseats.passengeraccountservice.domain.passenger;

import pl.flywithbookedseats.passengeraccountservice.domain.passenger.Passenger;

public interface BookingService {

    Passenger getPassenger(String email);
}
