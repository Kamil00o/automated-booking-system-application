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

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/auth")
public class AuthServiceController {

    private final AuthServiceApplicationService service;
    private final RegisterUserCommandMapper registerMapper;
    private final UserDtoMapper mapper;

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
}
