package pl.flywithbookedseats.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import pl.flywithbookedseats.api.passenger.UserDtoMapper;
import pl.flywithbookedseats.domain.auth.AuthService;
import pl.flywithbookedseats.domain.auth.PassengerAccountService;
import pl.flywithbookedseats.domain.user.EncodingService;
import pl.flywithbookedseats.external.service.passenger.FeignCreatePassengerCommandMapper;
import pl.flywithbookedseats.external.service.passenger.FeignPassengerDtoMapper;
import pl.flywithbookedseats.external.service.passenger.FeignPassengerService;
import pl.flywithbookedseats.external.service.passenger.PassengerAccountServiceAdapter;
import pl.flywithbookedseats.security.JWTUtil;

@Configuration
public class AuthServiceDomainConfig {

    @Bean
    public AuthService authService(
            AuthenticationManager authenticationManager,
            UserDtoMapper userDtoMapper,
            JWTUtil jwtUtil,
            PassengerAccountService passengerAccountService,
            EncodingService encodingService
    ) {
        return new AuthService(authenticationManager, userDtoMapper, jwtUtil, passengerAccountService, encodingService);
    }

    @Bean
    public PassengerAccountService passengerAccountService(
            FeignPassengerDtoMapper feignPassengerDtoMapper,
            FeignCreatePassengerCommandMapper feignCreatePassengerCommandMapper,
            FeignPassengerService feignPassengerService
    ) {
        return new PassengerAccountServiceAdapter(
                feignPassengerDtoMapper,
                feignCreatePassengerCommandMapper,
                feignPassengerService
        );
    }
}
