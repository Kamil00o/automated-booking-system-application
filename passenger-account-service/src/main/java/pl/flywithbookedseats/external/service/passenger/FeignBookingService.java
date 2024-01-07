package pl.flywithbookedseats.external.service.passenger;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.flywithbookedseats.api.passenger.PassengerDto;

@FeignClient(name = "seats-booking-system-service")
public interface FeignBookingService {

    @GetMapping(path = "/seats-booking/passenger/email/{email}")
    FeignPassengerDto getPassengerDtoData(@PathVariable String email);
}
