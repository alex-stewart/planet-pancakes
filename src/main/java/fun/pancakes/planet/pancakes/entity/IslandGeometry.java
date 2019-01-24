package fun.pancakes.planet.pancakes.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class IslandGeometry {

    private Double posX;

    private Double posY;

    private Double radius;
}
