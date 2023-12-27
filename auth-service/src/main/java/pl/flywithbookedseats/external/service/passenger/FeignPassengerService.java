package pl.flywithbookedseats.external.service.passenger;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.flywithbookedseats.api.passenger.RegisterUserCommand;

@FeignClient(name = "passenger-account-service")
public interface FeignPassengerService {

    @PostMapping(path = "/passengers")
    FeignPassengerDto createNewPassengerAccount(@Valid @RequestBody FeignPassengerDto feignPassengerDto);

    @GetMapping(path = "/passengers/email/{email}")
    FeignPassengerDto retrievePassengerAccountByEmail(@PathVariable String email);
}
