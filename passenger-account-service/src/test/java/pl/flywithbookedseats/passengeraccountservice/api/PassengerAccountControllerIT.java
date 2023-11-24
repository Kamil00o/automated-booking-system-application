package pl.flywithbookedseats.passengeraccountservice.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import pl.flywithbookedseats.passengeraccountservice.BaseIT;
import pl.flywithbookedseats.passengeraccountservice.PassengerAccountTestFactory;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.CreatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PagePassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.PassengerAccountServiceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testng.AssertJUnit.assertEquals;

class PassengerAccountControllerIT extends BaseIT {

    @Autowired
    PassengerAccountServiceImpl service;

    @Test
    void testCreateNewPassengerAccountOkStatus() {
        CreatePassengerAccountCommand createCommand = PassengerAccountTestFactory.createCreateCommand();

        var response = callHttpMethod(HttpMethod.POST,
                "/passengers",
                null,
                createCommand,
                PassengerAccountDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PassengerAccountDto body = response.getBody();
        assertNotNull(body);
        assertEqualsBodyAndCreateCommand(createCommand, body);
    }

    @Test
    void testRetrievePassengerAccountByIdOkStatus() {
        PassengerAccount testPassengerAccount = PassengerAccountTestFactory.createPassenger();
        service.createNewPassengerAccount(testPassengerAccount);

        var response = callHttpMethod(HttpMethod.GET,
                "/passengers" + service.retrievePassengerAccountByEmail(testPassengerAccount.getEmail()),
                null,
                testPassengerAccount,
                PassengerAccountDto.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        PassengerAccountDto body = response.getBody();
        assertNotNull(body);
        assertEqualsBodyAndPassengerAccount(testPassengerAccount, body);
    }

    @Test
    void testRetrieveAllPassengerAccountsFromDbOkStatus() {
        PassengerAccount testPassengerAccount = PassengerAccountTestFactory.createPassenger();
        service.createNewPassengerAccount(testPassengerAccount);

        var response = callHttpMethod(HttpMethod.GET,
                "/passengers",
                null,
                null,
                PagePassengerAccountDto.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        PagePassengerAccountDto body = response.getBody();
        assertNotNull(body);
        Assertions.assertEquals(1, body.passengerAccounts().size());
        Assertions.assertEquals(1, body.totalElements());
        Assertions.assertEquals(1, body.totalPages());
        Assertions.assertEquals(1, body.currentPage());
    }

    private void assertEqualsBodyAndPassengerAccount(PassengerAccount passedPassengerAccount,
                                                     PassengerAccountDto passedBody) {
        assertEquals(passedBody.email(), passedPassengerAccount.getEmail());
        assertEquals(passedBody.name(), passedPassengerAccount.getName());
        assertEquals(passedBody.birthDate(), passedPassengerAccount.getBirthDate());
        assertEquals(passedBody.surname(), passedPassengerAccount.getSurname());
        assertEquals(passedBody.disability(), passedPassengerAccount.isDisability());
    }

    private void assertEqualsBodyAndCreateCommand(CreatePassengerAccountCommand passedCreateCommand,
                                                     PassengerAccountDto passedBody) {
        assertEquals(passedBody.email(), passedCreateCommand.email());
        assertEquals(passedBody.name(), passedCreateCommand.name());
        assertEquals(passedBody.birthDate(), passedCreateCommand.birthDate());
        assertEquals(passedBody.surname(), passedCreateCommand.surname());
        assertEquals(passedBody.disability(), passedCreateCommand.disability());
    }
}
