package fun.pancakes.planet_pancakes.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder(toBuilder = true)
public class ProfileDto {
    private String id;
    private String name;
    private String location;

    private Long coins;
    private Map<String, Integer> resources;
}
