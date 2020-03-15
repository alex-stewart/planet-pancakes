package fun.pancakes.planet_pancakes.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

@Builder
@Data
public class ResourceDto {
    private String resourceName;
    private String category;
    private Long price;
    private Map<Instant, Long> priceHistory;
}
