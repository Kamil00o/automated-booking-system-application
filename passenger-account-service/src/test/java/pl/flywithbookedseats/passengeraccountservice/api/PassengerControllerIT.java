package pl.flywithbookedseats.passengeraccountservice.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import pl.flywithbookedseats.passengeraccountservice.BaseIT;
import pl.flywithbookedseats.passengeraccountservice.PassengerTestFactory;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.PagePassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.PassengerDto;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.Passenger;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.PassengerServiceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testng.AssertJUnit.assertEquals;

class PassengerControllerIT extends BaseIT {

    @Autowired
    PassengerServiceImpl service;

    @Test
    void testCreateNewPassengerAccountOkStatus() {
        CreatePassengerCommand createCommand = PassengerTestFactory.createCreateCommand();

        var response = callHttpMethod(HttpMethod.POST,
                "/passengers",
                null,
                createCommand,
                PassengerDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PassengerDto body = response.getBody();
        assertNotNull(body);
        assertEqualsBodyAndCreateCommand(createCommand, body);
    }

    @Test
    void testRetrievePassengerAccountByIdOkStatus() {
        Passenger testPassenger = PassengerTestFactory.createPassenger();
        service.createNewPassengerAccount(testPassenger);
        String id = service.retrievePassengerAccountByEmail(testPassenger.getEmail()).getId().toString();

        var response = callHttpMethod(HttpMethod.GET,
                "/passengers/" + id,
                null,
                testPassenger,
                PassengerDto.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        PassengerDto body = response.getBody();
        assertNotNull(body);
        assertEqualsBodyAndPassengerAccount(testPassenger, body);
    }

    @Test
    void testRetrieveAllPassengerAccountsFromDbOkStatus() {
        Passenger testPassenger = PassengerTestFactory.createPassenger();
        service.createNewPassengerAccount(testPassenger);

        var response = callHttpMethod(HttpMethod.GET,
                "/passengers",
                null,
                null,
                PagePassengerAccountDto.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        PagePassengerAccountDto body = response.getBody();
        assertNotNull(body);
        Assertions.assertEquals(1, body.passengers().size());
        Assertions.assertEquals(1, body.totalElements());
        Assertions.assertEquals(1, body.totalPages());
        Assertions.assertEquals(1, body.currentPage());
    }

    private void assertEqualsBodyAndPassengerAccount(Passenger passedPassenger,
                                                     PassengerDto passedBody) {
        assertEquals(passedBody.email(), passedPassenger.getEmail());
        assertEquals(passedBody.name(), passedPassenger.getName());
        assertEquals(passedBody.birthDate(), passedPassenger.getBirthDate());
        assertEquals(passedBody.surname(), passedPassenger.getSurname());
        assertEquals(passedBody.disability(), passedPassenger.isDisability());
    }

    private void assertEqualsBodyAndCreateCommand(CreatePassengerCommand passedCreateCommand,
                                                  PassengerDto passedBody) {
        assertEquals(passedBody.email(), passedCreateCommand.email());
        assertEquals(passedBody.name(), passedCreateCommand.name());
        assertEquals(passedBody.birthDate(), passedCreateCommand.birthDate());
        assertEquals(passedBody.surname(), passedCreateCommand.surname());
        assertEquals(passedBody.disability(), passedCreateCommand.disability());
    }
}
