package pl.flywithbookedseats.api.auth;

public record AuthenticationRequest(
        String email,
        String password
) {
}
