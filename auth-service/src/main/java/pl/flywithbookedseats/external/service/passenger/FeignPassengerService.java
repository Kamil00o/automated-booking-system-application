package pl.flywithbookedseats.external.service.passenger;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.flywithbookedseats.api.CreatePassengerCommand;

@FeignClient(name = "passenger-account-service")
public interface FeignPassengerService {

    @PostMapping(path = "/passengers")
    FeignPassengerDto createNewPassengerAccount(@Valid @RequestBody CreatePassengerCommand createPassengerCommand);
}
