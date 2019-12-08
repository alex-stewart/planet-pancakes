package fun.pancakes.planet_pancakes.dto;

import fun.pancakes.planet_pancakes.persistence.entity.Settlement;
import fun.pancakes.planet_pancakes.persistence.entity.WikiSection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private List<Settlement> cities;
    private List<Settlement> towns;
}
