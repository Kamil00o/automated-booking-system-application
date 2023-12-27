package pl.flywithbookedseats.api.auth;


import pl.flywithbookedseats.api.passenger.UserDto;

public record AuthenticationResponse(
        String token,
        UserDto userDto
) {
}
