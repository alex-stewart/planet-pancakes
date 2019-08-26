package fun.pancakes.planet_pancakes.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Point {
    private Double x;
    private Double y;
}
