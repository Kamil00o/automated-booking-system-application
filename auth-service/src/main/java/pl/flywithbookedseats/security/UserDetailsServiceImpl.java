package pl.flywithbookedseats.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.domain.auth.PassengerAccountService;
import pl.flywithbookedseats.domain.user.User;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PassengerAccountService service;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = service.getPassengerByEmail(email);

        return new UserDetailsImpl(user);
    }
}
