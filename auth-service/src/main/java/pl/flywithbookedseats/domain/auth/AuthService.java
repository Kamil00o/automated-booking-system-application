package pl.flywithbookedseats.domain.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import pl.flywithbookedseats.api.auth.AuthenticationRequest;
import pl.flywithbookedseats.api.auth.AuthenticationResponse;
import pl.flywithbookedseats.api.passenger.UserDto;
import pl.flywithbookedseats.api.passenger.UserDtoMapper;
import pl.flywithbookedseats.domain.user.EncodingService;
import pl.flywithbookedseats.domain.user.User;
import pl.flywithbookedseats.security.JWTUtil;
import pl.flywithbookedseats.security.UserDetailsImpl;

@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDtoMapper userDtoMapper;
    private final JWTUtil jwtUtil;
    private final PassengerAccountService passengerAccountService;
    private final EncodingService encodingService;

    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        UserDto userDto = userDtoMapper.toDto(principal.getUser());
        String token = jwtUtil.issueToken(userDto.email(), userDto.role());
        return new AuthenticationResponse(token, userDto);
    }

    public User register(User user) {
        User userWithEncodedPassword = user.withPassword(encodingService.encode(user.getPassword()));

        return passengerAccountService.registerNewPassenger(userWithEncodedPassword);
    }
}
