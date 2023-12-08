package pl.flywithbookedseats.external.service.passenger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.flywithbookedseats.api.passenger.PassengerDtoMapper;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.BookingService;

@RequiredArgsConstructor
@Slf4j
public class BookingAdapter implements BookingService {

    private final FeignBookingService feignBookingService;
    private final PassengerDtoMapper passengerDtoMapper;

    @Override
    public Passenger getPassenger(String email) {
        try {
            return passengerDtoMapper.toDomain(feignBookingService.getPassengerDtoData(email));
        } catch (Exception exception) {
            log.warn("ReservationIdList has not been retrieved from booking service.");
            return null;
        }
    }
}
