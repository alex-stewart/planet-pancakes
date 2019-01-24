package fun.pancakes.planet.pancakes.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "islands")
public class Island {

    @Id
    private String id;

    private String name;

    private Circle geometry;
}
