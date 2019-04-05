package fun.pancakes.planet.pancakes.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "islands")
public class Island {

    @Id
    private Integer id;

    private String name;

    private Double size;

    private Integer ring;

    private Double radius;

    private Double bearing;

    private WikiSection wiki;
}
