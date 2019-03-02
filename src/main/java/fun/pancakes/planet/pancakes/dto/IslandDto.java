package fun.pancakes.planet.pancakes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IslandDto {

    private String id;

    private String name;

    private String description;

    private Integer size;

    private Double radius;

    private Double bearing;
}
