package fun.pancakes.planet_pancakes.persistence.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@Document(collection = "price_history")
public class PriceHistory {

    @Id
    private String priceId;

    private String resourceName;
    private Date date;
    private Long price;
}
