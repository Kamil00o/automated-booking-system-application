package pl.flywithbookedseats.apigateway.configuration;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingFilter {

    @GetMapping(path = "/test")
    public String test() {
        return "test";
    }
}
