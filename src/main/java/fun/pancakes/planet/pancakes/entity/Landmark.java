package fun.pancakes.planet.pancakes.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Landmark {
    private String name;
    private String description;
    private Point location;
}
