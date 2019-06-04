package fun.pancakes.planet.pancakes.dto;

import fun.pancakes.planet.pancakes.entity.WikiSection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IslandDto {

    private Integer id;

    private String name;

    private Integer size;

    private Integer ring;

    private Double radius;

    private Double bearing;

    private WikiSection wiki;
}
