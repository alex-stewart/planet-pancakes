package fun.pancakes.planet_pancakes.persistence.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

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
    private Double wobbleRadius;
    private Integer wobbleCycleDays;
    private WikiSection wiki;
    private List<Settlement> cities;
    private List<Settlement> towns;
}
