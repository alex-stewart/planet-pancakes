package fun.pancakes.planet_pancakes.persistence.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "prices")
public class Price {

    @Id
    private Long id;

    private Long priceDate;
    private String resource;
    private Integer price;
}
