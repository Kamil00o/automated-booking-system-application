package pl.flywithbookedseats.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.domain.user.EncodingService;

@Component
@RequiredArgsConstructor
public class PasswordEncodingService implements EncodingService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
