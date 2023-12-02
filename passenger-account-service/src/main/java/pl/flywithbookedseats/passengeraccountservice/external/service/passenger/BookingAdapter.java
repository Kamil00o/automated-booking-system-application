package pl.flywithbookedseats.passengeraccountservice.external.service.passenger;

import lombok.RequiredArgsConstructor;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.PassengerDtoMapper;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.Passenger;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.BookingService;

@RequiredArgsConstructor
public class BookingAdapter implements BookingService {

    private final BookingPassengerDtoProxy bookingPassengerDtoProxy;
    private final PassengerDtoMapper passengerDtoMapper;

    @Override
    public Passenger getPassenger(String email) {
        return passengerDtoMapper.toDomain(bookingPassengerDtoProxy.getPassengerDtoData(email));
    }
}
