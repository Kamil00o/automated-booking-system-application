package pl.flywithbookedseats.domain.seatsscheme;

import org.springframework.data.domain.Pageable;

public interface SeatsSchemeRepository {

    SeatsScheme save(SeatsScheme seatsScheme);

    boolean existsByPlaneModelName(String planeModelName);

    SeatsScheme findByPlaneModelName(String planeModelName);

    SeatsScheme findById(Long id);

    void deleteById(Long id);

    void deleteByPlaneModelName(String planeModelName);

    void deleteAll();

    PageSeatsScheme findAll(final Pageable pageable);
}
