package pl.flywithbookedseats.passengeraccountservice.domain.passenger.service;

import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;

public interface BookingPassengerDtoProxyService {

    PassengerAccount getPassengerDtoFromBookingService(String email);
}
