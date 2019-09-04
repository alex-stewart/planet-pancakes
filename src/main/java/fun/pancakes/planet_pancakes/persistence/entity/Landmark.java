package fun.pancakes.planet_pancakes.persistence.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Landmark {
    private String name;
    private String description;
    private Point location;
}
