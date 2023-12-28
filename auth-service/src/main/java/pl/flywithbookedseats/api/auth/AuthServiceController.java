package pl.flywithbookedseats.api.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.AuthServiceApplication;
import pl.flywithbookedseats.appservices.AuthServiceApplicationService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/auth")
public class AuthServiceController {

    private final AuthServiceApplicationService service;

    @GetMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = service.login(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, response.token())
                .body(response);
    }
}
