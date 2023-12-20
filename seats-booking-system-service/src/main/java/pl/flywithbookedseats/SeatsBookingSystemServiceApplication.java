package pl.flywithbookedseats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SeatsBookingSystemServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeatsBookingSystemServiceApplication.class, args);
    }
}
