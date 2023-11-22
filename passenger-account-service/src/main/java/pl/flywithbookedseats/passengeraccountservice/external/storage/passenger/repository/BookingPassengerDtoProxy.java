package pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerAccountDto;

@FeignClient(name = "seats-booking-system-service")
public interface BookingPassengerDtoProxy {

    @GetMapping(path = "/seats-booking/get-passenger/email/{email}")
    PassengerAccountDto getPassengerDtoData(@PathVariable String email);
}
