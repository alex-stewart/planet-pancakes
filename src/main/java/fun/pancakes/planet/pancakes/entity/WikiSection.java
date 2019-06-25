package fun.pancakes.planet.pancakes.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WikiSection {
    private String heading;
    private String content;
    private List<WikiSection> subsections;
}
