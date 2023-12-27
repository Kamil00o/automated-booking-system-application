package pl.flywithbookedseats.domain.auth;

import pl.flywithbookedseats.domain.user.User;

public interface PassengerAccountService {

    User registerNewPassenger(User user);

    User getPassengerByEmail(String email);
}
