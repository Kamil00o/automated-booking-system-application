package pl.flywithbookedseats.api.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.api.passenger.RegisterUserCommand;
import pl.flywithbookedseats.api.passenger.RegisterUserCommandMapper;
import pl.flywithbookedseats.api.passenger.UserDto;
import pl.flywithbookedseats.api.passenger.UserDtoMapper;
import pl.flywithbookedseats.appservices.AuthServiceApplicationService;
import pl.flywithbookedseats.domain.user.User;
import pl.flywithbookedseats.security.Security;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/auth")
public class AuthServiceController {

    private final AuthServiceApplicationService service;
    private final RegisterUserCommandMapper registerMapper;
    private final UserDtoMapper mapper;
    private final Security security;

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = service.login(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, response.token())
                .body(response);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterUserCommand registerUserCommand) {
        User signedUser = service.register(registerMapper.toDomain(registerUserCommand));

        return ResponseEntity.ok(mapper.toDto(signedUser));
    }

    @GetMapping(path = "/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> aboutMe() {
        User user = security.getPrincipal();

        return ResponseEntity
                .ok(mapper.toDto(user));
    }
}
