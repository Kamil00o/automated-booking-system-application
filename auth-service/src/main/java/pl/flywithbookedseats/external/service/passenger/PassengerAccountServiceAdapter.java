package pl.flywithbookedseats.external.service.passenger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.flywithbookedseats.domain.user.UserNotCreatedException;
import pl.flywithbookedseats.domain.auth.PassengerAccountService;
import pl.flywithbookedseats.domain.user.User;
import pl.flywithbookedseats.domain.user.UserNotFoundException;

@Slf4j
@RequiredArgsConstructor
public class PassengerAccountServiceAdapter implements PassengerAccountService {

    private final FeignPassengerDtoMapper mapper;
    private final FeignCreatePassengerCommandMapper createMapper;
    private final FeignPassengerService service;

    @Override
    public User registerNewPassenger(User user) {
        try {
            FeignPassengerDto signedPassengerAccount = service.createNewPassengerAccount(createMapper.toCommand(user));

            return mapper.toDomain(signedPassengerAccount);
        } catch (Exception ex) {
            log.warn("Passenger Account has not been created: {}", ex);

            throw new UserNotCreatedException("Passenger account has not been created!");
        }
    }

    @Override
    public User getPassengerByEmail(String email) {
        try {
            FeignPassengerDto retrievedPassengerAccount = service.retrievePassengerAccountByEmail(email);

            return mapper.toDomain(retrievedPassengerAccount);
        } catch (Exception ex) {
            log.warn("Passenger account has not been found :{}", ex);

            throw new UserNotFoundException("Passenger Account has not been found!");
        }
    }
}
