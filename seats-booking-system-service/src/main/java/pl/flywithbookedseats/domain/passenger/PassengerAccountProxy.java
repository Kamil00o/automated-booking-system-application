package pl.flywithbookedseats.domain.passenger;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.flywithbookedseats.api.passenger.PassengerDto;

@FeignClient(name = "passenger-account-service")
public interface PassengerAccountProxy {

    @GetMapping(path = "/passenger-account/get/email/{email}")
    public PassengerDto getPassengerAccountDtoData(@PathVariable String email);
}
