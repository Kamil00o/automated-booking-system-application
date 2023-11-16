package pl.flywithbookedseats.passengeraccountservice.logic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PassengerAccountServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(PassengerAccountServiceApplication.class, args);
    }
}
