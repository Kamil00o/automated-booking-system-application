package pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.adapter;

import lombok.RequiredArgsConstructor;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper.PassengerAccountDtoMapper;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.BookingPassengerDtoProxyService;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.repository.BookingPassengerDtoProxy;

@RequiredArgsConstructor
public class BookingPassengerDtoProxyAdapter implements BookingPassengerDtoProxyService {

    private final BookingPassengerDtoProxy bookingPassengerDtoProxy;
    private final PassengerAccountDtoMapper passengerAccountDtoMapper;

    @Override
    public PassengerAccount getPassengerDtoFromBookingService(String email) {
        return passengerAccountDtoMapper.toDomain(bookingPassengerDtoProxy.getPassengerDtoData(email));
    }
}
