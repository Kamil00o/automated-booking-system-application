package pl.flywithbookedseats.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import pl.flywithbookedseats.BaseIT;
import pl.flywithbookedseats.PassengerTestFactory;
import pl.flywithbookedseats.api.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.api.passenger.PagePassengerDto;
import pl.flywithbookedseats.api.passenger.PassengerDto;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.PassengerService;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testng.AssertJUnit.assertEquals;

class PassengerControllerIT extends BaseIT {

    @Autowired
    PassengerService service;

    @Test
    void testCreateNewPassengerOkStatus() {
        CreatePassengerCommand createCommand = PassengerTestFactory.createCreateCommand();

        var response = callHttpMethod(HttpMethod.POST,
                "/seats-booking/passenger",
                null,
                createCommand,
                PassengerDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PassengerDto body = response.getBody();
        assertNotNull(body);
        assertEqualsBodyAndCreateCommand(createCommand, body);
    }

    @Test
    void testRetrievePassengerByIdOkStatus() {
        Passenger testPassenger = PassengerTestFactory.createPassenger();
        service.createNewPassenger(testPassenger);
        String id = service.retrievePassengerByEmail(testPassenger.getEmail()).getId().toString();

        var response = callHttpMethod(HttpMethod.GET,
                "/seats-booking/passenger/id/" + id,
                null,
                testPassenger,
                PassengerDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PassengerDto body = response.getBody();
        assertNotNull(body);
        assertEqualsBodyAndPassenger(testPassenger, body);
    }

    @Test
    void testRetrievePassengerByEmailOkStatus() {
        Passenger testPassenger = PassengerTestFactory.createPassenger();
        service.createNewPassenger(testPassenger);
        String email = service.retrievePassengerByEmail(testPassenger.getEmail()).getEmail();

        var response = callHttpMethod(HttpMethod.GET,
                "/seats-booking/passenger/email/" + email,
                null,
                testPassenger,
                PassengerDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PassengerDto body = response.getBody();
        assertNotNull(body);
        assertEqualsBodyAndPassenger(testPassenger, body);
    }

    @Test
    void testRetrieveAllPassengerFromDbOkStatus() {
        Passenger testPassenger = PassengerTestFactory.createPassenger();
        service.createNewPassenger(testPassenger);

        var response = callHttpMethod(HttpMethod.GET,
                "/seats-booking/passenger",
                null,
                null,
                PagePassengerDto.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        PagePassengerDto body = response.getBody();
        assertNotNull(body);
        Assertions.assertEquals(1, body.passengers().size());
        Assertions.assertEquals(1, body.totalElements());
        Assertions.assertEquals(1, body.totalPages());
        Assertions.assertEquals(1, body.currentPage());
    }

    private void assertEqualsBodyAndPassenger(Passenger passedPassenger,
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
