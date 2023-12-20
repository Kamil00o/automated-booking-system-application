package pl.flywithbookedseats.external.service.passenger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.PassengerAccountService;

@Slf4j
@RequiredArgsConstructor
public class PassengerAccountServiceAdapter implements PassengerAccountService {

    private final FeignPassengerService service;
    private final FeignPassengerDtoMapper mapper;

    @Override
    public Passenger getPassengerAccountData(String email) {
        try {
            return mapper.toDomain(service.getPassengerAccountDtoData(email));
        } catch (Exception e) {
            log.warn("PassengerDto has not been retrieved from passenger-account-service.");

            return null;
        }
    }
}
