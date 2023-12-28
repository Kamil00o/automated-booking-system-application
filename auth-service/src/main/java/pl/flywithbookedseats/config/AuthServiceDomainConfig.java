package pl.flywithbookedseats.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import pl.flywithbookedseats.api.passenger.UserDtoMapper;
import pl.flywithbookedseats.domain.auth.AuthService;
import pl.flywithbookedseats.security.JWTUtil;

@Configuration
public class AuthServiceDomainConfig {

    @Bean
    public AuthService authService(
            AuthenticationManager authenticationManager,
            UserDtoMapper userDtoMapper,
            JWTUtil jwtUtil
    ) {
        return new AuthService(authenticationManager, userDtoMapper, jwtUtil);
    }
}
