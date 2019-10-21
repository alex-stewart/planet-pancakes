package fun.pancakes.planet_pancakes.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ProfileDto {
    private String id;
    private String name;

    private Integer coins;

    private Integer wood;
    private Integer cloth;
    private Integer iron;
    private Integer coal;
}
