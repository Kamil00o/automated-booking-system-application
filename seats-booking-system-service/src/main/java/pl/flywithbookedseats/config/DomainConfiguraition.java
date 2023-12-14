package pl.flywithbookedseats.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeRepository;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeService;
import pl.flywithbookedseats.external.storage.seatsscheme.JpaSeatsSchemeRepository;
import pl.flywithbookedseats.external.storage.seatsscheme.JpaSeatsSchemeRepositoryMapper;
import pl.flywithbookedseats.external.storage.seatsscheme.SeatsSchemeStorageAdapter;

@Configuration
public class DomainConfiguraition {

    @Bean
    public SeatsSchemeService seatsSchemeService(
            SeatsSchemeRepository seatsSchemeRepository) {
        return new SeatsSchemeService(seatsSchemeRepository);
    }

    @Bean
    public SeatsSchemeRepository seatsSchemeRepository(
            JpaSeatsSchemeRepository jpaSeatsSchemeRepository,
            JpaSeatsSchemeRepositoryMapper jpaSeatsSchemeRepositoryMapper) {
        return new SeatsSchemeStorageAdapter(jpaSeatsSchemeRepository, jpaSeatsSchemeRepositoryMapper);
    }
}
