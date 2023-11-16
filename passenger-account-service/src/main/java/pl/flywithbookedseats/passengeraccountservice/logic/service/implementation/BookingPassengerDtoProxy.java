<<<<<<<< HEAD:passenger-account-service/src/main/java/pl/flywithbookedseats/passengeraccountservice/external/service/passenger/BookingPassengerDtoProxy.java
package pl.flywithbookedseats.passengeraccountservice.external.service.passenger;
========
package pl.flywithbookedseats.passengeraccountservice.logic.service.implementation;
>>>>>>>> 4cae5f3 (Caused by: java.lang.ClassNotFoundException:):passenger-account-service/src/main/java/pl/flywithbookedseats/passengeraccountservice/logic/service/implementation/BookingPassengerDtoProxy.java

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
<<<<<<<< HEAD:passenger-account-service/src/main/java/pl/flywithbookedseats/passengeraccountservice/external/service/passenger/BookingPassengerDtoProxy.java
import pl.flywithbookedseats.passengeraccountservice.api.passenger.PassengerDto;
========
import pl.flywithbookedseats.passengeraccountservice.logic.model.dto.PassengerAccountDto;
>>>>>>>> 4cae5f3 (Caused by: java.lang.ClassNotFoundException:):passenger-account-service/src/main/java/pl/flywithbookedseats/passengeraccountservice/logic/service/implementation/BookingPassengerDtoProxy.java

//TODO: CHANGE NAME
@FeignClient(name = "seats-booking-system-service")
public interface BookingPassengerDtoProxy {

    @GetMapping(path = "/seats-booking/get-passenger/email/{email}")
    PassengerDto getPassengerDtoData(@PathVariable String email);
}
