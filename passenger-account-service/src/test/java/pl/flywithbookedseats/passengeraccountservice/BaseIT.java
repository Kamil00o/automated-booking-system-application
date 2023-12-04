package pl.flywithbookedseats.passengeraccountservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.flywithbookedseats.PassengerAccountServiceApplication;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.PassengerService;
import pl.flywithbookedseats.external.storage.passenger.JpaPassengerRepository;

@ActiveProfiles("it")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = PassengerAccountServiceApplication.class
)
@ExtendWith(SpringExtension.class)
public class BaseIT {
    //Maybe it's better to make these fields final & use @RequiredArgsConstructor ?

    @Autowired
    protected Environment environment;
    @Autowired
    protected PassengerService passengerService;
    @Autowired
    protected TestRestTemplate restTemplate;
    @Autowired
    private ServerPortService serverPortService;
    @Autowired
    private JpaPassengerRepository jpaPassengerRepository;

    private final Passenger userPassenger = PassengerTestFactory.createPassenger();

    @BeforeEach
    void init() {
        jpaPassengerRepository.deleteAll();
    }

    protected String localUrl(String endpoint) {
        int port = serverPortService.getPort();
        return "http://localhost:" + port + endpoint;
    }

    protected void addTestPassengerAccounts() {

        passengerService.createNewPassenger(userPassenger);
    }

    protected <T, U> ResponseEntity<U> callHttpMethod(
            HttpMethod httpMethod,
            String url,
            String accessToken,
            T body,
            Class<U> mapToObject
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.AUTHORIZATION, accessToken);
        headers.add(HttpHeaders.ACCEPT, "application/json");
        HttpEntity<T> requestEntity;
        if (body == null) {
            requestEntity = new HttpEntity<>(headers);
        } else {
            requestEntity = new HttpEntity<>(body, headers);
        }
        return restTemplate.exchange(
                localUrl(url),
                httpMethod,
                requestEntity,
                mapToObject
        );
    }
}
