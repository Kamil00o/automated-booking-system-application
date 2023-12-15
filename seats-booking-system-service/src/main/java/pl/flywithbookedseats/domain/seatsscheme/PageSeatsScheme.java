package pl.flywithbookedseats.domain.seatsscheme;

import lombok.*;

import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageSeatsScheme {
    private List<SeatsScheme> seatsSchemes;
    private Integer currentPage;
    private Integer totalPages;
    private Long totalElements;
}
