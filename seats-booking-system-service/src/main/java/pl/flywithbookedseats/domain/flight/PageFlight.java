package pl.flywithbookedseats.domain.flight;

import lombok.*;

import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageFlight {

    private List<Flight> flights;
    private Integer currentPage;
    private Integer totalPages;
    private Long totalElements;
}
