package pl.flywithbookedseats.passengeraccountservice.external.service.passenger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.PassengerDtoMapper;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.Passenger;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.BookingService;

@RequiredArgsConstructor
@Slf4j
public class BookingAdapter implements BookingService {

    private final BookingPassengerDtoProxy bookingPassengerDtoProxy;
    private final PassengerDtoMapper passengerDtoMapper;

    @Override
    public Passenger getPassenger(String email) {
        try {
            return passengerDtoMapper.toDomain(bookingPassengerDtoProxy.getPassengerDtoData(email));
        } catch (Exception exception) {
            log.warn("ReservationIdList has not been retrieved from booking service.");
            return null;
        }
    }
}
