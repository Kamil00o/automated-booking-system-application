package pl.flywithbookedseats.passengeraccountservice.domain.passenger.service;

import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.Passenger;

public interface BookingDtoProxyService {

    Passenger getPassengerDtoFromBookingService(String email);
}
