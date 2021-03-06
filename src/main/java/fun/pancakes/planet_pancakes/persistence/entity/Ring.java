package fun.pancakes.planet_pancakes.persistence.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "rings")
public class Ring {

    @Id
    private Integer id;

    private Boolean clockwise;
    private Integer yearDays;
    private Double radius;
}
