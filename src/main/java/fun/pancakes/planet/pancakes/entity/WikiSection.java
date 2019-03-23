package fun.pancakes.planet.pancakes.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WikiSection {

    String heading;

    String content;

    List<WikiSection> subsections;
}
