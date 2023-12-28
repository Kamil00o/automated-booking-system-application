package pl.flywithbookedseats.appservices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.api.auth.AuthenticationRequest;
import pl.flywithbookedseats.api.auth.AuthenticationResponse;
import pl.flywithbookedseats.domain.auth.AuthService;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceApplicationService {

    private final AuthService service;

    public AuthenticationResponse login(AuthenticationRequest request) {
        return service.login(request);
    }
}
