package pl.flywithbookedseats.external.service.passenger;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.flywithbookedseats.api.passenger.PassengerDto;

@FeignClient(name = "passenger-account-service")
public interface FeignPassengerService {

    @GetMapping(path = "/passengers/email/{email}")
    public FeignPassengerDto getPassengerAccountDtoData(@PathVariable String email);

}
