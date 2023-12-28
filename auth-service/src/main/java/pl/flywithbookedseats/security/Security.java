package pl.flywithbookedseats.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.domain.auth.PassengerAccountService;
import pl.flywithbookedseats.domain.user.User;


@Component
@RequiredArgsConstructor
public class Security {

    private final PassengerAccountService service;

    public User getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return service.getPassengerByEmail(((UserDetailsImpl) authentication.getPrincipal()).getUsername());
    }
}
