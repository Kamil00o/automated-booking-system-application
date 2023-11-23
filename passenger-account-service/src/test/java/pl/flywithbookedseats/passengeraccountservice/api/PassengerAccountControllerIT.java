package pl.flywithbookedseats.passengeraccountservice.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import pl.flywithbookedseats.passengeraccountservice.BaseIT;
import pl.flywithbookedseats.passengeraccountservice.PassengerAccountTestFactory;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.PassengerAccountServiceImpl;

import static org.testng.AssertJUnit.assertEquals;

public class PassengerAccountControllerIT extends BaseIT {

    @Autowired
    PassengerAccountServiceImpl service;

    @Test
    public void testPassengerShouldObtainInformationAboutHisAccount() {
        PassengerAccount testPassengerAccount = PassengerAccountTestFactory.createPassenger();
        service.createNewPassengerAccount(testPassengerAccount);

        var response = callHttpMethod(HttpMethod.GET,
                "/passengers",
                null,
                null,
                PassengerAccountDto.class);

        PassengerAccountDto body = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}
