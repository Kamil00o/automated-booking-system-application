package pl.flywithbookedseats.eurekaserverlb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerLbApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerLbApplication.class, args);
    }
}
