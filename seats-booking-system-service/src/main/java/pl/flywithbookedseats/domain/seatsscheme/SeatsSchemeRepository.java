package pl.flywithbookedseats.domain.seatsscheme;

public interface SeatsSchemeRepository {

    SeatsScheme save(SeatsScheme seatsScheme);

    boolean existsByPlaneModelName(String planeModelName);

    SeatsScheme findByPlaneModelName(String planeModelName);

    SeatsScheme findById(Long id);

    void deleteById(Long id);

    void deleteByPlaneModelName(String planeModelName);

    void deleteAll();
}
