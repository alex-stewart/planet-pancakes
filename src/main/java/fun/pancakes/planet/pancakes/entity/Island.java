package fun.pancakes.planet.pancakes.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "islands")
public class Island {

    @Id
    private String id;

    private String name;

    private String description;

    private Double size;

    private List<Double> coordinates;
}
