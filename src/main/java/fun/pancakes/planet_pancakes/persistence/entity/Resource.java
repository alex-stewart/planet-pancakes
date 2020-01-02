package fun.pancakes.planet_pancakes.persistence.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Data
@Builder
@Document(collection = "resources")
public class Resource {

    @Id
    private String resourceName;

    private Long price;
    private Double priceTrendPercent;
    private Double priceMaxChangePercent;
    private Map<Date, Long> priceHistory;
}
