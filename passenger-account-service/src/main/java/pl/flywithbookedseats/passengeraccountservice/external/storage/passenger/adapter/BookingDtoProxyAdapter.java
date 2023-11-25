package pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.adapter;

import lombok.RequiredArgsConstructor;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper.PassengerDtoMapper;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.Passenger;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.BookingDtoProxyService;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.repository.BookingPassengerDtoProxy;

@RequiredArgsConstructor
public class BookingDtoProxyAdapter implements BookingDtoProxyService {

    private final BookingPassengerDtoProxy bookingPassengerDtoProxy;
    private final PassengerDtoMapper passengerDtoMapper;

    @Override
    public Passenger getPassengerDtoFromBookingService(String email) {
        return passengerDtoMapper.toDomain(bookingPassengerDtoProxy.getPassengerDtoData(email));
    }
}
